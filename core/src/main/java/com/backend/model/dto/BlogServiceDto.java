package com.backend.model.dto;

import com.backend.model.request.FindBlogRequestDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlogServiceDto {

    private String keyword;
    private String sort;
    private String page;
    private String size;

    public BlogServiceDto(FindBlogRequestDto requestDto) {
        this.keyword = requestDto.getKeyword();
        this.sort = requestDto.getSort();
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }
}
