package com.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends Exception{

    private String code;
    private String msg;
    private HttpStatus status;

    public CustomException(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.status = resultCode.getStatus();
    }

}
