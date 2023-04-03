package com.lio.BlogApi.models.dtos.response;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private LocalDateTime timestamp;
    private String message;
    private Integer statusCode;
    private HttpStatus status;
    private Boolean ok;
    private T data;
    private Map<String, String> error;

}
