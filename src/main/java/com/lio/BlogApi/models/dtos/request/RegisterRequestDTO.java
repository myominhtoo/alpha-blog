package com.lio.BlogApi.models.dtos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotNull(message = "{account.username.notNull}")
    @NotEmpty(message = "{account.username.notEmpty}")
    private String username;

    @NotNull(message = "{account.email.notNull}")
    @NotEmpty(message = "{account.email.notEmpty}")
    private String email;

    @NotNull(message = "{account.password.notNull}")
    @NotEmpty(message = "{account.password.notEmpty}")
    private String password;

    @NotNull(message = "{account.location.notNull}")
    @NotEmpty(message = "{account.location.notEmpty}")
    private String location;

}