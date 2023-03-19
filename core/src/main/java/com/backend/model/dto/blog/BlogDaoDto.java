package com.backend.model.dto.blog;

import com.backend.model.dto.blog.kakao.KakaoBlogResponseDto;
import com.backend.model.vo.BlogDocument;
import com.backend.model.vo.BlogMeta;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BlogDaoDto {

    private List<BlogDocument> documents;
    private BlogMeta meta;

    public BlogDaoDto(KakaoBlogResponseDto kakaoBlogResponseDto) {
        this.documents = kakaoBlogResponseDto.getDocuments();
        this.meta = kakaoBlogResponseDto.getMeta();
    }

}
