package com.example.model.blog.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class KakaoBlogDocumentApiClientResponseDto {

    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private Timestamp datetime;

}
