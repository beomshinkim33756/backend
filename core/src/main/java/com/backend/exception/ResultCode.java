package com.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ResultCode {

    SUCCESS("00000", "성공", HttpStatus.OK),
    SYSTEM_ERROR("19999", "시스템 에러", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAM_MANIPULATION("10001", "파라미터 변조 에러", HttpStatus.BAD_REQUEST)
    ;

    private String code;
    private String msg;
    private HttpStatus status;
}