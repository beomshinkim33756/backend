package com.example.service;

import com.example.model.blog.BlogRequestDto;
import com.example.model.blog.BlogResponseDto;

public interface ApiService {

    BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) throws Exception;
}
