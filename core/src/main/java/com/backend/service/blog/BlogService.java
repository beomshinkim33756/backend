package com.backend.service.blog;

import com.backend.model.dto.service.BlogServiceDto;

import java.util.List;

public interface BlogService {

    List findBlogList(BlogServiceDto blogServiceDto) throws Exception;
}
