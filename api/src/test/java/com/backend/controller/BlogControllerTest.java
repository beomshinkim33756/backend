package com.backend.controller;

import com.backend.exception.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
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
public class BlogControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    private final String SEARCH_BLOG_URI = "/api/find/blog";
    private final String SEARCH_RANK_URI = "/api/find/rank";

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
        MultiValueMap<String, String> parms = new LinkedMultiValueMap<>();
        parms.add("sort", "accuracy");
        parms.add("page", "ass");
        parms.add("size", "10");
        mockMvc.perform(get(SEARCH_BLOG_URI).params(parms).characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.resultCode").value(ResultCode.PARAM_MANIPULATION.getCode()))
                .andDo(MockMvcResultHandlers.print());
    }
}
