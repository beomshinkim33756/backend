package com.example.model.blog.kakao;

import com.example.enums.SortType;
import com.example.model.blog.BlogRequestDto;
import com.example.prop.KakaoBlogProp;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoBlogApiClientRequestDto {

    private String host;
    private String token;
    private String query;
    private String sort;
    private String page;
    private String size;

    public KakaoBlogApiClientRequestDto(BlogRequestDto blogRequestDto, KakaoBlogProp kakaoBlogProp) {
        this.host = kakaoBlogProp.getKakaoBlogUrl();
        this.token = "KakaoAK " + kakaoBlogProp.getKakaoRestKey();
        this.query = blogRequestDto.getKeyword();
        if (blogRequestDto.getSort().equals(SortType.ACCURACY)) {
            this.sort = "accuracy";
        } else if (blogRequestDto.getSort().equals(SortType.RECENCY)) {
            this.sort = "recency";
        }
        this.page = blogRequestDto.getPage();
        this.size = blogRequestDto.getSize();
    }
}
