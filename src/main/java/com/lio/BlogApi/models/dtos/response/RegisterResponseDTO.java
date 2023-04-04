package com.lio.BlogApi.models.dtos.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {

    private String accountId;
    private String email;
    private String username;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdDate;

    private String location;

}
