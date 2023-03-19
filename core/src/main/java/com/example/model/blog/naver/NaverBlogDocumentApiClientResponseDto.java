package com.example.model.blog.naver;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class NaverBlogDocumentApiClientResponseDto {

    private String title;
    private String description;
    private String link;
    private String bloggername;
    private String bloggerlink;
    private Timestamp postdate;
}
