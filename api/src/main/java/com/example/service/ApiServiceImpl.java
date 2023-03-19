package com.example.service;

import com.example.model.blog.BlogResponseDto;
import com.example.model.blog.BlogRequestDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.naver.NaverBlogApiClientRequestDto;
import com.example.naver.NaverBlogApiClient;
import com.example.prop.KakaoBlogProp;
import com.example.kakao.KakaoBlogApiClient;
import com.example.prop.NaverBlogProp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final KakaoBlogApiClient kakaoBlogApiClient;
    private final NaverBlogApiClient naverBlogApiClient;
    private final KakaoBlogProp kakaoBlogProp;
    private final NaverBlogProp naverBlogProp;

    @Override
    public BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) {
        BlogResponseDto blogResponseDto = kakaoBlogApiClient.findBlog(new KakaoBlogApiClientRequestDto(blogRequestDto, kakaoBlogProp)); // 카카오 블로그 호출
        if (blogResponseDto == null) { // 카카오톡 블로그 불러오기 실패시
             blogResponseDto = naverBlogApiClient.findBlog(new NaverBlogApiClientRequestDto(blogRequestDto, naverBlogProp)); // 네이버 블로그 호출
        }
        return blogResponseDto;
    }
}
