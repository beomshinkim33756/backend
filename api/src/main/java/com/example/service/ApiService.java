package com.example.service;

import com.example.model.dto.blog.BlogRequestDto;
import com.example.model.dto.blog.BlogDto;

public interface ApiService {

    BlogDto findBlogList(BlogRequestDto blogRequestDto) throws Exception;
}
