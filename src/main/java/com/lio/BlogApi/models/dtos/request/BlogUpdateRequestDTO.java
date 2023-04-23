package com.lio.BlogApi.models.dtos.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogUpdateRequestDTO {

    @NotNull(message = "{blogTitle.notnull}")
    @NotEmpty(message = "{blogTitle.notempty}")
    @Length(min = 10, max = 50, message = "{blogTitle.notrange}")
    private String title;

    @NotNull(message = "{blogContent.notnull}")
    @NotEmpty(message = "{blogContent.notempty}")
    @Length(min = 100, max = 100000, message = "{blogContent.notrange}")
    private String content;

    @NotNull(message = "{categoryId.notnull}")
    @NotEmpty(message = "{categoryId.notempty}")
    @Length(message = "{categoryId.notrange}", min = 10, max = 10)
    private String categoryId;

}
