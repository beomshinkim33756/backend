package com.example.model.blog.kakao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@ToString
@Slf4j
public class KakaoBlogApiClientResponseDto {

    private List<KakaoBlogDocumentApiClientResponseDto> documents;
    private KakaoBlogMetaApiClientResponseDto meta;
}
