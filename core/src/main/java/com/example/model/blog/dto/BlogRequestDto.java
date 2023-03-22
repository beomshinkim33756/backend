package com.example.model.blog.dto;

import com.example.enums.SortType;
import com.example.model.http.request.FindBlogRequestDto;
import com.example.model.http.request.FindUrlBlogRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BlogRequestDto {

    private String query;
    private SortType sort;
    private String page;
    private String size;

    public BlogRequestDto(FindBlogRequestDto requestDto) {
        this.query = requestDto.getKeyword().trim();
        this.sort = SortType.of(requestDto.getSort());
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }


    public BlogRequestDto(FindUrlBlogRequestDto requestDto) {
        this.query = new StringBuffer().append(requestDto.getUrl().trim()).append(" ").append(requestDto.getKeyword().trim()).toString();
        this.sort = SortType.of(requestDto.getSort());
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }
}
