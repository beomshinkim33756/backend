package com.backend.model.response;

import com.backend.exception.ResultCode;
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
    private boolean isEnd;
    private Long totalCount;
    private String resultCode;
    private String msg;

    public FindBlogResponseDto(List documents, boolean isEnd, Long totalCount, ResultCode resultCode) {
        this.documents = documents;
        this.isEnd = isEnd;
        this.totalCount = totalCount;
        this.resultCode = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

}
