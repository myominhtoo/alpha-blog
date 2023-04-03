package com.lio.BlogApi.models.dtos.custom;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTemplate {

    private String template;
    private String content;
    private LocalDateTime createdDate;

}
