package com.example.model.blog.dto;

import com.example.enums.SortType;
import com.example.model.http.request.FindBlogRequestDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlogRequestDto {

    private String keyword;
    private SortType sort;
    private String page;
    private String size;

    public BlogRequestDto(FindBlogRequestDto requestDto) {
        this.keyword = requestDto.getKeyword();
        this.sort = SortType.of(requestDto.getSort());
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }
}
