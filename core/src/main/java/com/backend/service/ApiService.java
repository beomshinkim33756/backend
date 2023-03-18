package com.backend.service;

import com.backend.model.dto.BlogServiceDto;
import com.backend.model.dto.BlogDaoDto;

public interface ApiService {

    BlogDaoDto findBlogList(BlogServiceDto blogServiceDto) throws Exception;
}
