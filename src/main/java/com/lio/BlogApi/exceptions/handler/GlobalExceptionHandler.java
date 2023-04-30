package com.lio.BlogApi.exceptions.handler;

import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApiResponse<?>> globalExceptionHandler( Exception e ){
        return new ResponseEntity<ApiResponse<?>>(
                ResponseUtil.globalException() ,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
