package com.example.controller;

import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogResponseDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@Slf4j
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiService apiService;

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
        params.add("page", "ass");
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
        when(apiService.findBlogList(any())).thenReturn(new BlogResponseDto());
        mockMvc.perform(get(SEARCH_BLOG_URI).params(params).characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.SUCCESS.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }
}
