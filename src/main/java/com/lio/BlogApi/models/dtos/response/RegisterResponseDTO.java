package com.lio.BlogApi.models.dtos.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDTO {

    private String accountId;
    private String email;
    private String username;
    private String phone;
    private LocalDateTime createdDate;
    private String location;

}
