package com.example.controller;

import com.example.enums.EnterpriseType;
import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.service.ApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
public class ApiControllerTest2 {

    private static final ExecutorService service = Executors.newFixedThreadPool(100);

    @Autowired
    private ApiController apiController;

    @Test
    @DisplayName("블로그 API 호출 성공 케이스")
    void blog_test_5() throws Exception {
        int threadNumber = 10; // 스레드 개수
        int cycle = 3; // 사이클
        CountDownLatch latch = new CountDownLatch(threadNumber);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        // 동기메소드 처리
        for (int i=0 ; i < threadNumber ; i++ ) {
            service.execute(() -> {
                for (int j=0; j <cycle ; j++) {
                    try {
                        apiController.findBlog(new FindBlogRequestDto(UUID.randomUUID().toString(), "0", "10", "10"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                latch.countDown();
            });
        }

        latch.await();
        stopWatch.stop();

        System.out.println("성능 테스트 : " + stopWatch.getTotalTimeMillis() + " ms");

        /**
         * 키워드 카운팅 동기 비동기 처리
         * 응답 속도 비동기 성능 우위
         */
    }

}
