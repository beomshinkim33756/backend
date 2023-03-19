package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortType {

    ACCURACY(0), // 정확도
    RECENCY(1) // 최신순
    ;

    int code;

    public static SortType of(Integer code) {
        if (code == null) {
            return null;
        }

        for (SortType val : values()) {
            if (val.getCode() == code) {
                return val;
            }
        }
        return null;
    }
}
