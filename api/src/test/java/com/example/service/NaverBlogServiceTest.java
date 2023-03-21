package com.example.service;

import com.example.enums.EnterpriseType;
import com.example.enums.SortType;
import com.example.kakao.KakaoBlogApiClient;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class NaverBlogServiceTest {

    @Autowired
    private ApiService apiService;

    @MockBean
    private KakaoBlogApiClient kakaoBlogApiClient;


    @Test
    @DisplayName("블로그 서비스 호출 네이버 조회")
    void blog_test_1() throws Exception {
        when(kakaoBlogApiClient.findBlog(any())).thenReturn(null);
        BlogResponseDto blogResponseDto = apiService.findBlogList(
                new BlogRequestDto(
                        "keyword",
                        SortType.RECENCY,
                        "10",
                        "10"
                )
        );

        assertEquals(EnterpriseType.NAVER, blogResponseDto.getEnterprise());
        assertEquals(false, blogResponseDto.getIsEnd());
    }

    @Test
    @DisplayName("블로그 서비스 호출 네이버 조회 & 마지막 페이지")
    void blog_test_2() throws Exception {
        when(kakaoBlogApiClient.findBlog(any())).thenReturn(null);
        BlogResponseDto blogResponseDto = apiService.findBlogList(
                new BlogRequestDto(
                        "keyword",
                        SortType.RECENCY,
                        "50",
                        "10"
                )
        );
        assertEquals(EnterpriseType.NAVER, blogResponseDto.getEnterprise());
        assertEquals(true, blogResponseDto.getIsEnd());
    }
}
