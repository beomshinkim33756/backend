package com.example.client;

import com.example.enums.EnterpriseType;
import com.example.enums.SortType;
import com.example.kakao.KakaoBlogApiClient;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.prop.KakaoBlogProp;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KakaoBlogApiClientTest {

    @Autowired
    private KakaoBlogApiClient kakaoBlogApiClient;

    @Autowired
    private KakaoBlogProp kakaoBlogProp;

    @Test
    @DisplayName("카카오 API 호출")
    void kakao_blog_api_client_test_1() throws Exception {
        BlogResponseDto blogResponseDto = kakaoBlogApiClient.findBlog(
                new KakaoBlogApiClientRequestDto(
                        kakaoBlogProp.getKakaoBlogUrl(),
                        "KakaoAK " + kakaoBlogProp.getKakaoRestKey(),
                        "keyword",
                        "accuracy",
                        "10",
                        "10",
                        "cachekey"
                )
        );
        assertNotNull(blogResponseDto);
        assertEquals(EnterpriseType.KAKAO, blogResponseDto.getEnterprise());
        assertTrue(blogResponseDto.getDocuments().size() > 0);
    }

}
