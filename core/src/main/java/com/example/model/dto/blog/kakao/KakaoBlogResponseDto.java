package com.example.model.dto.blog.kakao;

import com.example.model.vo.BlogDocument;
import com.example.model.vo.BlogMeta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@ToString
@Slf4j
public class KakaoBlogResponseDto {

    private List<BlogDocument> documents;
    private BlogMeta meta;
}
