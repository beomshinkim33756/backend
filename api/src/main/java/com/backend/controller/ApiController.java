package com.backend.controller;

import com.backend.exception.CustomException;
import com.backend.exception.ResultCode;
import com.backend.model.dto.blog.BlogRequestDto;
import com.backend.model.dto.blog.BlogDto;
import com.backend.model.request.FindBlogRequestDto;
import com.backend.model.response.FindBlogResponseDto;
import com.backend.service.ApiService;
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
        BlogDto blogDto = apiService.findBlogList(new BlogRequestDto(requestDto.checkForgery()));
        return ResponseEntity.ok().body(new FindBlogResponseDto(blogDto, ResultCode.SUCCESS));
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank(

    ) throws CustomException {
        return ResponseEntity.ok().body(null);
    }
}
