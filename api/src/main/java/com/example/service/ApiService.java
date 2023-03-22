package com.example.service;

import com.example.entities.KeywordTb;
import com.example.model.blog.dto.BlogRequestDto;
import com.example.model.blog.dto.BlogResponseDto;
import com.example.model.keyword.dto.KeywordResponseDto;

import java.util.concurrent.Future;

public interface ApiService {

    BlogResponseDto findBlogList(BlogRequestDto blogRequestDto) ;
    KeywordResponseDto findKeywordRank() ;
    Future<KeywordTb> incrementCount(String keyword);
}
