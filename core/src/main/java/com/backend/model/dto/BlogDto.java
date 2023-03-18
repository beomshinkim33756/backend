package com.backend.model.dto;

import com.backend.model.request.SearchBlogRequestDto;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
public class BlogDto {

    private String keyword;
    private String sort;
    private String page;
    private String size;

    public BlogDto(SearchBlogRequestDto requestDto) {
        this.keyword = requestDto.getKeyword();
        this.sort = requestDto.getSort();
        this.page = requestDto.getPage();
        this.size = requestDto.getSize();
    }
}
