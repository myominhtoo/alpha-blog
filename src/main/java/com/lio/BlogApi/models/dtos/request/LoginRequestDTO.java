package com.lio.BlogApi.models.dtos.request;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}