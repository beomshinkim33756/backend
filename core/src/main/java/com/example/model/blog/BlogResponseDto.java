package com.example.model.blog;

import com.example.model.blog.kakao.KakaoBlogApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogApiClientResponseDto;
import com.example.model.blog.vo.BlogDocument;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BlogResponseDto {

    private List<BlogDocument> documents;


    public BlogResponseDto(KakaoBlogApiClientResponseDto kakaoBlogApiClientResponseDto) {

    }

    public BlogResponseDto(NaverBlogApiClientResponseDto naverBlogApiClientResponseDto) {

    }

}
