package com.example.service;

import com.example.model.dto.blog.BlogDto;
import com.example.model.dto.blog.BlogRequestDto;
import com.example.model.dto.blog.kakao.KakaoBlogRequestDto;
import com.example.prop.KakaoBlogProp;
import com.example.kakao.KakaoBlogApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final KakaoBlogApiClient kakaoBlogApiClient;
    private final KakaoBlogProp kakaoBlogProp;
    @Override
    public BlogDto findBlogList(BlogRequestDto blogRequestDto) throws Exception {
        BlogDto blogDto = kakaoBlogApiClient.findBlogByKakao(new KakaoBlogRequestDto(blogRequestDto, kakaoBlogProp));
        if (blogDto == null) {
            // 네이버 호출
        }
        return blogDto;
    }
}
