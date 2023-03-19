package com.example.model.blog.naver;

import com.example.model.vo.BlogDocument;
import com.example.model.vo.BlogMeta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@Slf4j
public class NaverBlogApiClientResponseDto {

    private List<NaverBlogDocumentApiClientResponseDto> items;
    private Timestamp lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
}
