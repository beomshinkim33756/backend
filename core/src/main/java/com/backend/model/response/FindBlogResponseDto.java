package com.backend.model.response;

import com.backend.exception.ResultCode;
import com.backend.model.dto.BlogDaoDto;
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

    private List documents;
    private Boolean isEnd;
    private Long totalCount;
    private String resultCode;
    private String msg;

    public FindBlogResponseDto(BlogDaoDto blogDaoDto, ResultCode resultCode) {
        this.documents = blogDaoDto.getDocuments();
        this.isEnd = blogDaoDto.getIsEnd();
        this.totalCount = blogDaoDto.getTotalCount();
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
