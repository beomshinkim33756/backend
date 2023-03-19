package com.example.model.dto.blog;

import com.example.model.dto.blog.kakao.KakaoBlogResponseDto;
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

    public BlogDto(KakaoBlogResponseDto kakaoBlogResponseDto) {
        this.documents = kakaoBlogResponseDto.getDocuments();
        this.meta = kakaoBlogResponseDto.getMeta();
    }

}
