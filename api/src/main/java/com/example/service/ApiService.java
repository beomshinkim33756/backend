package com.example.service;

import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;

public interface ApiService {

    BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) throws Exception;
}
