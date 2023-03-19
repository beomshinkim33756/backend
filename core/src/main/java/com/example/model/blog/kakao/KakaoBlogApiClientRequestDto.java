package com.example.model.blog.kakao;

import com.example.enums.SortType;
import com.example.model.blog.dto.BlogRequestDto;
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
    private String cacheKey;

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
        StringBuffer sb =new StringBuffer().append("blog:").append(this.query).append(":").append(this.page).append(":").append(this.size).append(":").append(this.sort);
        this.cacheKey = sb.toString();
    }
}
