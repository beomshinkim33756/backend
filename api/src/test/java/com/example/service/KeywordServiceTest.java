package com.example.service;

import com.example.entities.KeywordTb;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.repositories.KeywordTbRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
        int threadNumber = 100; // 스레드 개수
        int cycle = 1000; // 사이클
        int keywordCount = 10; // 키워드 개수
        int totalCount = threadNumber * cycle; // 스레드 * 사이클

        ArrayList<String> words = generateWords(keywordCount);
        CountDownLatch latch = new CountDownLatch(threadNumber);
        AtomicInteger errorCnt = new AtomicInteger();

        // 동기메소드 처리
        for (int i=0 ; i < threadNumber ; i++ ) {
            service.execute(() -> {
                for (int j=0 ; j< cycle; j++) {
                    try {
                        String word = words.get((int)(System.currentTimeMillis()% keywordCount));
                        apiService.incrementCount(word);
                    } catch (DataIntegrityViolationException e) {
                        System.out.println("INSERT 안된 키워드 동시 INSERT 유니크 오류");
                        errorCnt.getAndIncrement();
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        assertKeywordCount(errorCnt.get(), totalCount); // 동시성 체크
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
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(); // 가장많이 입력된 10개 키워드 count 조회
        int count = keywordResponseDto.getRanks().stream().mapToInt(it -> Math.toIntExact(it.getCount())).sum();
        assertEquals(count + errorCount, totalCount); // 유니크 키 문제 COUNT + 전체 INSERT COUNT 합
    }

}
