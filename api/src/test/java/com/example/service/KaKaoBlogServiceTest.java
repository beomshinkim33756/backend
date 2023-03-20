package com.example.service;

import com.example.enums.SortType;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class KaKaoBlogServiceTest {

    @Autowired
    private ApiService apiService;


    @Test
    @DisplayName("카카오 블로그 API 호출")
    void blog_test_1() throws Exception {
        BlogResponseDto blogResponseDto = apiService.findBlogList(
                new BlogRequestDto(
                        "keyword",
                        SortType.RECENCY,
                        "10",
                        "10"
                )
        );
        System.out.println(blogResponseDto);
    }
}