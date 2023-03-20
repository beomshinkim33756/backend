package com.example.model.keyword.dto;

import com.example.entities.KeywordTb;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KeywordRankDto {

    private String keyword;
    private Long count;

    public KeywordRankDto(KeywordTb keywordTb) {
        this.keyword = keywordTb.getKeyword();
        this.count = keywordTb.getCount();
    }
}
