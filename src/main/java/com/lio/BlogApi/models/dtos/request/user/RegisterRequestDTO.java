package com.lio.BlogApi.models.dtos.request.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotNull(message = "{username.notnull}")
    @NotEmpty(message = "{username.notempty}")
    @Length(min = 3, max = 20, message = "{username.notrange}")
    private String username;

    @NotNull(message = "{email.notnull}")
    @NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.notformat}")
    private String email;

    @NotNull(message = "{password.notnull}")
    @NotEmpty(message = "{password.notempty}")
    @Length(min = 5, max = 20, message = "{password.notrange}")
    private String password;

    @NotNull(message = "{location.notnull}")
    @NotEmpty(message = "{location.notempty}")
    @Length(min = 8, max = 20, message = "{location.notrange}")
    private String location;

}