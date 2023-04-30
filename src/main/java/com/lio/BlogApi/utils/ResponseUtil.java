package com.lio.BlogApi.utils;

import java.time.LocalDateTime;
import java.util.Map;

import com.lio.BlogApi.models.enums.Message;
import org.springframework.http.HttpStatus;

import com.lio.BlogApi.models.dtos.response.general.ApiResponse;

public class ResponseUtil {

    public static <T> ApiResponse<?> response(
            HttpStatus status,
            Integer statusCode,
            String message,
            T data) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .ok(true)
                .build();
    }

    public static <T> ApiResponse<?> errorResponse(
            HttpStatus status,
            Integer statusCode,
            String message,
            Map<String, String> error) {
        return ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .statusCode(statusCode)
                .message(message)
                .ok(false)
                .error(error)
                .build();
    }


    public static <T> ApiResponse<?> invalidRequest(){
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.INVALID_REQUEST.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value())
        );
    }

    public static <T> ApiResponse<?> invalidRequestBody(){
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.INVALID_REQUEST_BODY.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST_BODY.value())
        );
    }

    public static <T> ApiResponse<?> success(){
        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.SUCCESS.value(),
                null
        );
    }

    public static <T> ApiResponse<?> success( String message ){
        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                message,
                null
        );
    }

    public static <T> ApiResponse<?> success( String message , T data ){
        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                message,
                data
        );
    }

    public static <T> ApiResponse<?> badRequest( String message , Map<String,String> errorMap ){
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                message,
                errorMap
        );
    }

    public static <T> ApiResponse<?> globalException(){
        return ResponseUtil.errorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Message.UNEXPECTED_ERROR.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.UNEXPECTED_ERROR.value())
        );
    }

}
