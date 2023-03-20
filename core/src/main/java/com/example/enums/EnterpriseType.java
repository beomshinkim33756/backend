package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnterpriseType {

    KAKAO("KAKAO"),
    NAVER("NAVER")
    ;


    private String code;
}
