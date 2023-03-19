package com.example.controller;

import com.example.exception.CustomException;
import com.example.exception.ResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalApiExceptionController {

    @ExceptionHandler
    public ResponseEntity<HashMap> handle(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException]", e);
        return exceptionRes(e);
    }

    @ExceptionHandler
    public ResponseEntity<HashMap> handle(BindException e) {
        log.error("[BindException]", e);
        return exceptionRes(e);
    }

    @ExceptionHandler
    public ResponseEntity<HashMap> handle(HttpMessageNotReadableException e) {
        log.error("[HttpMessageNotReadableException]", e);
        return exceptionRes(e);
    }


    @ExceptionHandler
    public ResponseEntity<HashMap> handle(RuntimeException e) {
        log.error("[RuntimeException]", e);
        return exceptionRes(e);
    }

    private <T> ResponseEntity<HashMap> exceptionRes(T e) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("resultCode", ResultCode.SYSTEM_ERROR.getCode());
        body.put("msg", ResultCode.SYSTEM_ERROR.getMsg());
        body.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(ResultCode.SYSTEM_ERROR.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
    }

    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<HashMap> handle(CustomException e) {
        HashMap<String, Object> body = new HashMap<>();
        body.put("resultCode", e.getCode());
        body.put("msg", e.getMsg());
        body.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(e.getStatus()).contentType(MediaType.APPLICATION_JSON).body(body);
    }
}
