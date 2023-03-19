package com.backend.service;

import com.backend.model.dto.blog.BlogRequestDto;
import com.backend.model.dto.blog.BlogDto;

public interface ApiService {

    BlogDto findBlogList(BlogRequestDto blogRequestDto) throws Exception;
}
