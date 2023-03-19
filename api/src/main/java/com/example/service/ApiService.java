package com.example.service;

import com.example.model.blog.BlogRequestDto;
import com.example.model.blog.BlogDto;

public interface ApiService {

    BlogDto findBlogList(BlogRequestDto blogRequestDto) throws Exception;
}
