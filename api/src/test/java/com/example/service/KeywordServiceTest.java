package com.example.service;

import com.example.entities.KeywordTb;
import com.example.enums.CacheType;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.repositories.KeywordTbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KeywordServiceTest {

    private static final ExecutorService service = Executors.newFixedThreadPool(100);

    @Autowired
    private ApiService apiService;

    @Autowired
    private KeywordTbRepository keywordTbRepository;

    @Test
    @DisplayName("키워드 증가")
    void keyword_test_1() throws Exception {
        apiService.incrementCount("keyword");
    }

    @Test
    @DisplayName("키워드 랭킹 호출")
    void keyword_test_2() throws Exception {
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank();
        System.out.println(keywordResponseDto);
    }

    @Test
    @DisplayName("키워드 랭킹 동시성 처리 확인")
    void keyword_test_3() throws Exception {
        int threadNumber = 10; // 스레드 개수
        int cycle = 3; // 사이클
        int keywordCount = 10; // 키워드 개수
        int totalCount = threadNumber * cycle; // 스레드 * 사이클

        ArrayList<String> words = generateWords(keywordCount);
        CountDownLatch latch = new CountDownLatch(threadNumber);
        AtomicInteger errorCnt = new AtomicInteger();
        keywordTbRepository.deleteAll();

        // 동기메소드 처리
        for (int i=0 ; i < threadNumber ; i++ ) {
            service.execute(() -> {
                for (int j=0 ; j< cycle; j++) {
                    try {
                        Future<KeywordTb> future = apiService.incrementCount(words.get((int) (System.currentTimeMillis() % keywordCount)));
                        future.get(); // 비동기 대기
                    } catch (Exception e) {
                        System.out.println("INSERT 안된 키워드 동시 INSERT 유니크 오류");
                        errorCnt.getAndIncrement();
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        assertKeywordCount(errorCnt.get(), totalCount); // 동시성 체크

        /**
         *  synchronized, 트랜잭션 lock 테스트 결과
         *  트랜잭션 lock이 월등히 성능이 좋다
         */
    }

    private ArrayList<String> generateWords(int count) { // 단어 생성
        ArrayList<String> words = new ArrayList<>();
        while (words.size() < count) { // 키워드 생성
            String word = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            if (!words.contains(word)) words.add(word);
        }
        return words;
    }

    /**
     * 키워드 테이블 INSERT 유니크 에러 허용 (발생 가능성이 낮으며, 소량의 정확성 맞춤 < 성능 우선 처리)
     * 이후 counting lock 처리
     * 유니크 에러 발생 카운트 + counting == 총 수행 수 동일성 체크
     * @param errorCount
     * @param totalCount
     */
    private void assertKeywordCount(int errorCount, int totalCount) { // 동시성 체크
        List<KeywordRankDto> ranks = keywordTbRepository.findTop10ByOrderByCountDesc().stream().map(it -> new KeywordRankDto(it, 0)).collect(Collectors.toList());
        int count = ranks.stream().mapToInt(it -> Math.toIntExact(it.getCount())).sum();
        System.out.println(errorCount);
        System.out.println(count);
        assertEquals(count + errorCount, totalCount); // 유니크 키 문제 COUNT + 전체 INSERT COUNT 합
    }

}
