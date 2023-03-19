package com.example.prop;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class KakaoBlogProp {

    @Value("${kakao.blog.url}")
    private String kakaoBlogUrl;

    @Value("${kakao.rest.key}")
    private String kakaoRestKey;
}
