package com.lio.BlogApi.models.dtos.request.review;

import lombok.Data;

@Data
public class ReviewRequestDTO {

    private String content;
    private String accountId;
    private float rating;

}
