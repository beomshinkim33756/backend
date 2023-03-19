package com.backend.model.response;

import com.backend.exception.ResultCode;
import com.backend.model.dto.blog.BlogDto;
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

    public FindBlogResponseDto(BlogDto blogDto, ResultCode resultCode) {
        this.documents = blogDto.getDocuments();
        this.meta = blogDto.getMeta();
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
