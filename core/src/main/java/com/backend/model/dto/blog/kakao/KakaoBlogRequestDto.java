package com.backend.model.dto.blog.kakao;

import com.backend.model.dto.blog.BlogRequestDto;
import com.backend.prop.KakaoBlogProp;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoBlogRequestDto {

    private String host;
    private String token;
    private String query;
    private String sort;
    private String page;
    private String size;

    public KakaoBlogRequestDto(BlogRequestDto blogRequestDto, KakaoBlogProp kakaoBlogProp) {
        this.host = kakaoBlogProp.getKakaoBlogUrl();
        this.token = "KakaoAK " + kakaoBlogProp.getKakaoRestKey();
        this.query = blogRequestDto.getKeyword();
        this.sort = blogRequestDto.getSort();
        this.page = blogRequestDto.getPage();
        this.size = blogRequestDto.getSize();
    }
}
