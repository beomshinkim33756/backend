package com.backend.controller;

import com.backend.exception.CustomException;
import com.backend.model.request.FindBlogRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BlogController {

    @GetMapping("/api/find/blog")
    public ResponseEntity findBlog(
            @Validated FindBlogRequestDto requestDto
    ) throws CustomException {
        FindBlogRequestDto findBlogRequestDto = requestDto.checkForgery();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/api/find/rank")
    public ResponseEntity findRank(

    ) throws CustomException {
        return ResponseEntity.ok().body(null);
    }
}
