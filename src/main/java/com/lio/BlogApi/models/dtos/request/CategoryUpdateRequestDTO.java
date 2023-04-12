package com.lio.BlogApi.models.dtos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CategoryUpdateRequestDTO {

    @NotNull(message = "{categoryId.notnull}")
    @NotEmpty(message = "{categoryId.notempty}")
    @Length(min = 10, message = "{categoryId.notrange}")
    private String categoryId;

    @NotNull(message = "{categoryName.notnull}")
    @NotEmpty(message = "{categoryName.notempty}")
    @Length(min = 5, max = 30, message = "{categoryName.notrange}")
    private String categoryName;

}
