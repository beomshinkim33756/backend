package com.backend.service;

import com.backend.model.dto.blog.BlogServiceDto;
import com.backend.model.dto.blog.BlogDaoDto;

public interface ApiService {

    BlogDaoDto findBlogList(BlogServiceDto blogServiceDto) throws Exception;
}
