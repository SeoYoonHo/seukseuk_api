package com.lsyh.seukseuk.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lsyh.seukseuk.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URISyntaxException;

@Slf4j
@RestControllerAdvice("com.lsyh.seukseuk.controller")//@ControllerAdvice + @ResponseBody
public class ExControllerAdvice {

    //URISyntaxException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(URISyntaxException.class)
    public CommonResponse.NoDataResponse exHandle(URISyntaxException e) {
        log.error("[exceptionHandle] ex", e);
        return CommonResponse.NoDataResponse.of("Bad Request", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonProcessingException.class)
    public CommonResponse.NoDataResponse exHandle(JsonProcessingException e) {
        log.error("[exceptionHandle] ex", e);
        return CommonResponse.NoDataResponse.of("Bad Request", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public CommonResponse.NoDataResponse exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return CommonResponse.NoDataResponse.of("EX", "내부 오류");
    }
}
