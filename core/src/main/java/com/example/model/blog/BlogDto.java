package com.example.model.blog;

import com.example.model.blog.kakao.KakaoBlogApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogApiClientResponseDto;
import com.example.model.vo.BlogDocument;
import com.example.model.vo.BlogMeta;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BlogDto {

    private List<BlogDocument> documents;
    private BlogMeta meta;

    public BlogDto(KakaoBlogApiClientResponseDto kakaoBlogApiClientResponseDto) {

    }

    public BlogDto(NaverBlogApiClientResponseDto naverBlogApiClientResponseDto) {

    }

}
