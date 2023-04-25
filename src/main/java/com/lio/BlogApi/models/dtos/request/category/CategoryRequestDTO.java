package com.lio.BlogApi.models.dtos.request.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotNull(message = "{message.notnull}")
    @NotBlank(message = "{message.notblank}")
    @Length(min = 5, max = 30, message = "{categoryName.notrange}")
    private String categoryName;

}
