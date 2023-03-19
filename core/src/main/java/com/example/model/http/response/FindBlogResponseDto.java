package com.example.model.http.response;

import com.example.exception.ResultCode;
import com.example.model.blog.BlogResponseDto;
import com.example.model.blog.vo.BlogDocument;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindBlogResponseDto {

    private List<BlogDocument> documents;
    private Integer totalCount;
    private Integer pageableCount;
    private Boolean isEnd;
    private Integer size;
    private Integer page;
    private Integer totalPage;
    private String resultCode;
    private String msg;

    public FindBlogResponseDto(BlogResponseDto blogResponseDto, ResultCode resultCode) {
        this.documents = blogResponseDto.getDocuments();
        this.isEnd = blogResponseDto.getIsEnd();
        this.totalCount = blogResponseDto.getTotalCount();
        this.pageableCount = blogResponseDto.getPageableCount();
        this.totalPage = blogResponseDto.getTotalPage();
        this.size = blogResponseDto.getSize();
        this.page = blogResponseDto.getPage();
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
