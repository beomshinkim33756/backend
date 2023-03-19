package com.example.model.blog.naver;

import com.example.enums.SortType;
import com.example.model.blog.BlogRequestDto;
import com.example.prop.KakaoBlogProp;
import com.example.prop.NaverBlogProp;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class NaverBlogApiClientRequestDto {

    private String host;
    private String clientId;
    private String clientSecret;
    private String query;
    private String sort;
    private String start;
    private String display;

    public NaverBlogApiClientRequestDto(BlogRequestDto blogRequestDto, NaverBlogProp naverBlogProp) {
        this.host = naverBlogProp.getNaverBlogUrl();
        this.clientId = naverBlogProp.getNaverClientId();
        this.clientSecret = naverBlogProp.getNaverClientSecret();
        this.query = blogRequestDto.getKeyword();
        if (blogRequestDto.getSort().equals(SortType.ACCURACY)) {
            this.sort = "sim";
        } else if (blogRequestDto.getSort().equals(SortType.RECENCY)) {
            this.sort = "date";
        }
        this.start = blogRequestDto.getPage();
        this.display = blogRequestDto.getSize();
    }
}