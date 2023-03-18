package com.backend.service;

import com.backend.model.dto.BlogServiceDto;

import java.util.List;

public interface ApiService {

    List findBlogList(BlogServiceDto blogServiceDto) throws Exception;
}
