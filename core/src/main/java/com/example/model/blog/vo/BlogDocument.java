package com.example.model.blog.vo;

import com.example.model.blog.kakao.KakaoBlogApiClientRequestDto;
import com.example.model.blog.kakao.KakaoBlogDocumentApiClientResponseDto;
import com.example.model.blog.naver.NaverBlogDocumentApiClientResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class BlogDocument {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp datetime;

    public BlogDocument(KakaoBlogDocumentApiClientResponseDto kakaoBlogDocumentApiClientResponseDto) {
        this.title = kakaoBlogDocumentApiClientResponseDto.getTitle();
        this.contents = kakaoBlogDocumentApiClientResponseDto.getContents();
        this.url = kakaoBlogDocumentApiClientResponseDto.getUrl();
        this.blogname = kakaoBlogDocumentApiClientResponseDto.getBlogname();
        this.thumbnail = kakaoBlogDocumentApiClientResponseDto.getThumbnail();
        this.datetime = kakaoBlogDocumentApiClientResponseDto.getDatetime();
    }

    public BlogDocument(NaverBlogDocumentApiClientResponseDto naverBlogDocumentApiClientResponseDto) {
        this.title = naverBlogDocumentApiClientResponseDto.getTitle();
        this.contents = naverBlogDocumentApiClientResponseDto.getDescription();
        this.url = naverBlogDocumentApiClientResponseDto.getBloggerlink();
        this.blogname = naverBlogDocumentApiClientResponseDto.getBloggername();
        this.datetime = naverBlogDocumentApiClientResponseDto.getPostdate();
    }

}
