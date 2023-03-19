package com.backend.model.dto.blog.kakao;

import com.backend.model.dto.blog.BlogServiceDto;
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

    public KakaoBlogRequestDto(BlogServiceDto blogServiceDto, KakaoBlogProp kakaoBlogProp) {
        this.host = kakaoBlogProp.getKakaoBlogUrl();
        this.token = "KakaoAK " + kakaoBlogProp.getKakaoRestKey();
        this.query = blogServiceDto.getKeyword();
        this.sort = blogServiceDto.getSort();
        this.page = blogServiceDto.getPage();
        this.size = blogServiceDto.getSize();
    }
}
