package com.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PageSize {

    MIN_PAGE(1),
    MAX_PAGE(50)

    ;

    int code;
}
