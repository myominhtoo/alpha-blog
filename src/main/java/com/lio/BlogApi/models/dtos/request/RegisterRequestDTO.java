package com.lio.BlogApi.models.dtos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotNull(message = "{username.notnull}")
    @NotEmpty(message = "{username.notempty}")
    private String username;

    @NotNull(message = "{email.notnull}")
    @NotEmpty(message = "{email.notempty}")
    private String email;

    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notempty}")
    private String password;

    @NotNull(message = "{location.notnull}")
    @NotEmpty(message = "{location.notempty}")
    private String location;

}