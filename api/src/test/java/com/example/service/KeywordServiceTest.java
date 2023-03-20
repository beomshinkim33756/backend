package com.example.service;

import com.example.model.keyword.dto.KeywordResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class KeywordServiceTest {

    @Autowired
    private ApiService apiService;

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
        int cycle = 1000; // 사이클
        int keywordCount = 10; // 키워드 개수

        ArrayList<String> words = new ArrayList<>();
        Thread[] threads = new Thread[threadNumber];

        while (words.size() < keywordCount) { // 키워드 생성
            String word = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            if (!words.contains(word)) words.add(word);
        }

        for (int i=0 ; i < threads.length ; i++ ) {
            threads[i] = new Thread(() -> {
               for (int j=0 ; j< cycle; j++) {
                   apiService.incrementCount(words.get((int)(System.currentTimeMillis()% keywordCount)));
               }
            });
            threads[i].start();
        } // 10개의 thread가 1000번씩 10개 키워드 입력

        try {
            Thread.sleep(threadNumber * cycle); // 스레드 슬립
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(); // 가장많이 입력된 10개 키워드 count 조회
        int count = keywordResponseDto.getRanks().stream().mapToInt(it -> Math.toIntExact(it.getCount())).sum();
        assertEquals(count, threadNumber * cycle);
    }


    @Test
    @DisplayName("키워드 랭킹 확인")
    void keyword_test_4() throws Exception {
        int threadNumber = 10; // 스레드 개수
        int cycle = 1000; // 사이클
        int keywordCount = 100; // 키워드 개수

        ArrayList<String> words = new ArrayList<>();
        Thread[] threads = new Thread[threadNumber];

        while (words.size() < keywordCount) { // 키워드 생성
            String word = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            if (!words.contains(word)) words.add(word);
        }

        for (int i=0 ; i < threads.length ; i++ ) {
            threads[i] = new Thread(() -> {
                for (int j=0 ; j< cycle; j++) {
                    apiService.incrementCount(words.get((int)(System.currentTimeMillis() % keywordCount)));
                }
            });
            threads[i].start();
        } // 10개의 thread가 1000번씩 10개 키워드 입력

        try {
            Thread.sleep(threadNumber * cycle); // 스레드 슬립
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(); // 가장많이 입력된 10개 키워드 count 조회
        System.out.println(keywordResponseDto.getRanks());
    }

}
