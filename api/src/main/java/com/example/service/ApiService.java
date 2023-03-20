package com.example.service;

import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;

public interface ApiService {

    BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) ;

    KeywordResponseDto findKeywordRank() ;

    void  incrementCount(String keyword);
}
