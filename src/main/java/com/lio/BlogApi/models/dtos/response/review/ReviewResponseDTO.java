package com.lio.BlogApi.models.dtos.response.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponseDTO {

    private String reviewId;

    private String content;

    @JsonFormat( pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime createdDate;

    @JsonFormat( pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime updatedDate;

    private String accountId;

    private String accountName;

}
