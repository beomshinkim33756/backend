package com.backend.model.response;

import com.backend.exception.ResultCode;
import com.backend.model.dto.blog.BlogDaoDto;
import com.backend.model.vo.BlogMeta;
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
    private BlogMeta meta;
    private String resultCode;
    private String msg;

    public FindBlogResponseDto(BlogDaoDto blogDaoDto, ResultCode resultCode) {
        this.documents = blogDaoDto.getDocuments();
        this.meta = blogDaoDto.getMeta();
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
