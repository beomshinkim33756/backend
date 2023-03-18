package com.backend.controller;

import com.backend.exception.CustomException;
import com.backend.exception.ResultCode;
import com.backend.model.dto.BlogServiceDto;
import com.backend.model.dto.BlogDaoDto;
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
        BlogDaoDto blogDaoDto = apiService.findBlogList(new BlogServiceDto(requestDto.checkForgery()));
        return ResponseEntity.ok().body(new FindBlogResponseDto(blogDaoDto, ResultCode.SUCCESS));
    }

    @GetMapping("/api/v1/find/rank")
    public ResponseEntity findRank(

    ) throws CustomException {
        return ResponseEntity.ok().body(null);
    }
}
