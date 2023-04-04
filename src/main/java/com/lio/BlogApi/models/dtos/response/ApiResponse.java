package com.lio.BlogApi.models.dtos.response;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private Integer statusCode;
    private HttpStatus status;
    private Boolean ok;
    private T data;
    private Map<String, String> error;

}
