package com.example.model.dto.blog;

import com.example.model.request.FindBlogRequestDto;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BlogRequestDto {

    private String keyword;
    private String sort;
    private String page;
    private String size;

    public BlogRequestDto(FindBlogRequestDto requestDto) {
        this.keyword = requestDto.getKeyword();
        this.sort = requestDto.getSort();
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }
}
