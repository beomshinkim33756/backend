package com.example.model.http.response;

import com.example.exception.ResultCode;
import com.example.model.blog.BlogResponseDto;
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
    private String resultCode;
    private String msg;

    public FindBlogResponseDto(BlogResponseDto blogResponseDto, ResultCode resultCode) {
        this.documents = blogResponseDto.getDocuments();
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
