package com.example.controller;

import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.http.response.FindBlogResponseDto;
import com.example.model.http.response.FindRankResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.example.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ApiController {

    private final ApiService apiService;

    @GetMapping("/api/v1/find/blog")
    public ResponseEntity findBlog(
            @Validated FindBlogRequestDto requestDto
    ) throws Exception {
        apiService.incrementCount(requestDto.getKeyword()); // 키워드 조회 값 증가 (비동기 처리)
        BlogResponseDto blogResponseDto = apiService.findBlogList(new BlogRequestDto(requestDto.checkForgery())); // 블로그 리스트 조회
        return ResponseEntity.ok().body(new FindBlogResponseDto(blogResponseDto, ResultCode.SUCCESS));
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank() {
        KeywordResponseDto keywordResponseDto = apiService.findKeywordRank(new KeywordResponseDto()); // 키워드 랭크 조회 및 업데이트
        return ResponseEntity.ok().body(new FindRankResponseDto(keywordResponseDto.getRanks(), ResultCode.SUCCESS));
    }
}
