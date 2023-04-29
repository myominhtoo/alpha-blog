package com.lio.BlogApi.models.dtos.request.review;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ReviewRequestDTO {

    @NotNull( message = "{reviewContent.notnull}")
    @NotEmpty( message = "{reviewContent.notempty}")
    @Length( max = 300 , message = "{reviewContent.notrange}")
    private String content;

    @NotNull( message = "{accountId.notnull}")
    @NotEmpty( message = "{accountId.notempty}")
    private String accountId;

    @NotNull( message = "{reviewRating.notnull}")
    @Range(min = 0 , max = 5 , message = "{reviewRating.notrange}")
    private float rating;

}
