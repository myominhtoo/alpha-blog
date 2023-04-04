package com.lio.BlogApi.models.dtos.custom;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTemplate {

    private String template;
    private String content;
    private LocalDateTime createdDate;
    private Map<String, String> replaceWords;

}
