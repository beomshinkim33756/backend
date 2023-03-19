package com.example.controller;

import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import com.example.model.blog.BlogRequestDto;
import com.example.model.blog.BlogResponseDto;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.http.response.FindBlogResponseDto;
import com.example.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        BlogResponseDto blogResponseDto = apiService.findBlogList(new BlogRequestDto(requestDto.checkForgery()));
        return ResponseEntity.ok().body(new FindBlogResponseDto(blogResponseDto, ResultCode.SUCCESS));
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank(

    ) throws CustomException {
        return ResponseEntity.ok().body(null);
    }
}
