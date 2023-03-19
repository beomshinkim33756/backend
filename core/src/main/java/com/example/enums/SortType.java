package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortType {

    ACCURACY("0"), // 정확도
    RECENCY("1") // 최신순
    ;

    String code;

    public static SortType of(String code) {
        if (code == null) {
            return null;
        }

        for (SortType val : values()) {
            if (val.getCode().equals(code)) {
                return val;
            }
        }
        return null;
    }
}
