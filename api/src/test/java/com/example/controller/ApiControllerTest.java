package com.example.controller;

import com.example.enums.EnterpriseType;
import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
public class ApiControllerTest {

    private static final ExecutorService service = Executors.newFixedThreadPool(100);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

    @Autowired
    private ApiController apiController;

    private final String SEARCH_BLOG_URI = "/api/v1/find/blog"; // 블로그 조회
    private final String SEARCH_RANK_URI = "/api/v1/find/rank"; // 인기 검색어 조회

    @Test
    @DisplayName("블로그 API 호출 파라미터 NULL 오류")
    void blog_test_1() throws Exception {
        mockMvc.perform(get(SEARCH_BLOG_URI))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SYSTEM_ERROR.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("블로그 API 호출 파라미터 변조")
    void blog_test_2() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sort", "0");
        params.add("page", "ass"); // 변조
        params.add("size", "10");
        params.add("keyword", "keyword");
        mockMvc.perform(get(SEARCH_BLOG_URI).params(params).characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.PARAM_MANIPULATION.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("블로그 API 호출 성공 케이스")
    void blog_test_3() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sort", "0");
        params.add("page", "10");
        params.add("size", "10");
        params.add("keyword", "keyword");
        BlogResponseDto blogResponseDto = new BlogResponseDto();
        blogResponseDto.setEnterprise(EnterpriseType.KAKAO);
        when(apiService.findBlogList(any())).thenReturn(blogResponseDto);
        mockMvc.perform(get(SEARCH_BLOG_URI).params(params).characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("블로그 API 호출 실패 케이스")
    void blog_test_4() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("sort", "0");
        params.add("page", "10");
        params.add("size", "10");
        params.add("keyword", "keyword");
        when(apiService.findBlogList(any())).thenReturn(null);
        mockMvc.perform(get(SEARCH_BLOG_URI).params(params).characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.FAIL_BLOG_LOADING.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("인기 검색어 목록 API 조회 성공")
    void rank_test_1 () throws Exception {
        when(apiService.findKeywordRank()).thenReturn(new KeywordResponseDto(Arrays.asList(new KeywordRankDto("123", 23L, 1))));
        mockMvc.perform(get(SEARCH_RANK_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("인기 검색어 목록 API 실패")
    void rank_test_2 () throws Exception {
        when(apiService.findKeywordRank()).thenReturn(new KeywordResponseDto());
        mockMvc.perform(get(SEARCH_RANK_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.NOT_EXIST_RANK.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("블로그 API 호출 성공 케이스")
    void blog_test_5() throws Exception {
        int threadNumber = 100; // 스레드 개수
        int cycle = 1000; // 사이클
        CountDownLatch latch = new CountDownLatch(threadNumber);
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        // 동기메소드 처리
        for (int i=0 ; i < threadNumber ; i++ ) {
            service.execute(() -> {

                try {
                    apiController.findBlog(new FindBlogRequestDto("keyword", "0", " 10", "10"));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
        }

        latch.await();
        stopWatch.stop();

        System.out.println("성능 테스트 : " + stopWatch.getTotalTimeMillis() + " ms");
    }

}
