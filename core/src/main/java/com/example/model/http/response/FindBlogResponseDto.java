package com.example.model.http.response;

import com.example.exception.ResultCode;
import com.example.model.blog.dto.BlogResponseDto;
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

    private Integer totalCount;
    private Integer totalPage;
    private Integer page;
    private Integer size;
    private Boolean isEnd;
    private String enterprise;
    private List<BlogDocument> documents;
    private String resultCode;
    private String msg;


    public FindBlogResponseDto(BlogResponseDto blogResponseDto) {
        if (blogResponseDto == null) { // 블로그 조회 실패
            this.resultCode = ResultCode.FAIL_BLOG_LOADING.getCode();
            this.msg = ResultCode.FAIL_BLOG_LOADING.getMsg();
        } else {
            this.documents = blogResponseDto.getDocuments();
            this.isEnd = blogResponseDto.getIsEnd();
            this.totalCount = blogResponseDto.getTotalCount();
            this.totalPage = blogResponseDto.getTotalPage();
            this.size = blogResponseDto.getSize();
            this.page = blogResponseDto.getPage();
            this.enterprise = blogResponseDto.getEnterprise().getCode();
            this.resultCode = ResultCode.SUCCESS.getCode();
            this.msg = ResultCode.SUCCESS.getMsg();
        }

    }
}
