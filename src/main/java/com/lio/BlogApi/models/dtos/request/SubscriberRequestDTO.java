package com.lio.BlogApi.models.dtos.request;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SubscriberRequestDTO {

    @Email(message = "{email.notformat}")
    @Length(min = 5, max = 30, message = "{email.notrange}")
    private String email;

    @Length(min = 5, max = 20, message = "{location.notrange}")
    private String location;

}
