package com.example.service;

import com.example.entities.KeywordTb;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.naver.NaverBlogApiClientRequestDto;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.naver.NaverBlogApiClient;
import com.example.prop.KakaoBlogProp;
import com.example.kakao.KakaoBlogApiClient;
import com.example.prop.NaverBlogProp;
import com.example.repositories.KeywordTbRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final KakaoBlogApiClient kakaoBlogApiClient;
    private final NaverBlogApiClient naverBlogApiClient;
    private final KakaoBlogProp kakaoBlogProp;
    private final NaverBlogProp naverBlogProp;
    private final KeywordTbRepository keywordTbRepository;

    /**
     * 카카오 API조회 실패시 네이버 API 조회
     *
     * 카카오 API 이외 검색 소스 추가시 해당 소스 external-api 추가후
     * 교체 혹은 언터페이스 교체
     *
     * @param blogRequestDto
     * @return
     */
    @Override
    public BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) {
        BlogResponseDto blogResponseDto = kakaoBlogApiClient.findBlog(new KakaoBlogApiClientRequestDto(blogRequestDto, kakaoBlogProp)); // 카카오 블로그 호출
        if (blogResponseDto == null) { // 카카오톡 블로그 불러오기 실패시
            log.debug("[네이버 블로그 조회] =======>");
            blogResponseDto = naverBlogApiClient.findBlog(new NaverBlogApiClientRequestDto(blogRequestDto, naverBlogProp)); // 네이버 블로그 호출
        }
        return blogResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public KeywordResponseDto findKeywordRank(KeywordResponseDto keywordResponseDto) {
        List<KeywordRankDto> ranks = keywordTbRepository.findTop10ByOrderByCountDesc().stream().map(KeywordRankDto::new).collect(Collectors.toList());
        return new KeywordResponseDto(ranks);
    }

    @Override
    @Transactional
    @Async
    public void incrementCount(String keyword) {
        if (StringUtils.isBlank(keyword)) return;
        log.debug("키워드 : {} 값 증가", keyword);
        KeywordTb keywordTb = keywordTbRepository.findByKeyword(keyword);
        if (keywordTb != null) {
            keywordTb.setCount(keywordTb.getCount() + 1);
        } else {
            keywordTb = new KeywordTb();
            keywordTb.setKeyword(keyword);
            keywordTb.setCount(1L);
        }
        keywordTbRepository.save(keywordTb);
    }
}
