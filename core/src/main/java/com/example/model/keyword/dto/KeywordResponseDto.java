package com.example.model.keyword.dto;

import com.example.exception.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class KeywordResponseDto {

    List<KeywordRankDto> ranks;
}
