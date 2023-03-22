package com.example.model.blog.dto;

import com.example.model.blog.kakao.KakaoBlogDocumentApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogDocumentApiClientResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class BlogDocumentDto {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp datetime;

    public BlogDocumentDto(KakaoBlogDocumentApiClientResponseDto kakaoBlogDocumentApiClientResponseDto) {
        this.title = kakaoBlogDocumentApiClientResponseDto.getTitle();
        this.contents = kakaoBlogDocumentApiClientResponseDto.getContents();
        this.url = kakaoBlogDocumentApiClientResponseDto.getUrl();
        this.blogname = kakaoBlogDocumentApiClientResponseDto.getBlogname();
        this.thumbnail = kakaoBlogDocumentApiClientResponseDto.getThumbnail();
        this.datetime = kakaoBlogDocumentApiClientResponseDto.getDatetime();
    }

    public BlogDocumentDto(NaverBlogDocumentApiClientResponseDto naverBlogDocumentApiClientResponseDto) {
        this.title = naverBlogDocumentApiClientResponseDto.getTitle();
        this.contents = naverBlogDocumentApiClientResponseDto.getDescription();
        this.url = naverBlogDocumentApiClientResponseDto.getBloggerlink();
        this.blogname = naverBlogDocumentApiClientResponseDto.getBloggername();
        this.datetime = naverBlogDocumentApiClientResponseDto.getPostdate();
    }

}
