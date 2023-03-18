package com.backend.controller;

import com.backend.exception.CustomException;
import com.backend.model.request.SearchBlogRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class BlogController {

    @GetMapping("/api/search/blog")
    public ResponseEntity searchBlog(
            @Validated SearchBlogRequestDto requestDto
    ) throws CustomException {
        requestDto.checkForgery();
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/api/search/rank")
    public ResponseEntity searchRank(

    ) throws CustomException {
        return ResponseEntity.ok().body(null);
    }
}
