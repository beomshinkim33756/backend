package com.example.client;

import com.example.enums.EnterpriseType;
import com.example.kakao.KakaoBlogApiClient;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.naver.NaverBlogApiClientRequestDto;
import com.example.naver.NaverBlogApiClient;
import com.example.prop.KakaoBlogProp;
import com.example.prop.NaverBlogProp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NaverBlogApiClientTest {

    @Autowired
    private NaverBlogApiClient naverBlogApiClient;

    @Autowired
    private NaverBlogProp naverBlogProp;

    @Test
    @DisplayName("네이버 API 호출")
    void kakao_blog_api_client_test_1() throws Exception {
        BlogResponseDto blogResponseDto = naverBlogApiClient.findBlog(
                new NaverBlogApiClientRequestDto(
                        naverBlogProp.getNaverBlogUrl(),
                        naverBlogProp.getNaverClientId(),
                        naverBlogProp.getNaverClientSecret(),
                        "keyword",
                        "sim",
                        "10",
                        "10"
                )
        );
        assertNotNull(blogResponseDto);
        assertEquals(EnterpriseType.NAVER, blogResponseDto.getEnterprise());
        assertTrue(blogResponseDto.getDocuments().size() > 0);
    }

}
