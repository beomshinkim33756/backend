package com.example.model.http.response;

import com.example.exception.ResultCode;
import com.example.model.keyword.dto.KeywordRankDto;
import com.example.model.keyword.dto.KeywordResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindRankResponseDto {

    List<KeywordRankDto> ranks;
    private String resultCode;
    private String msg;

    public FindRankResponseDto(KeywordResponseDto keywordResponseDto) {
        if (keywordResponseDto == null) {
            this.resultCode = ResultCode.NOT_EXIST_RANK.getCode();
            this.msg = ResultCode.NOT_EXIST_RANK.getMsg();
        } else {
            this.ranks = keywordResponseDto.getRanks();
            this.resultCode = ResultCode.SUCCESS.getCode();
            this.msg = ResultCode.SUCCESS.getMsg();
        }
    }
}
