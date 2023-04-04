package com.lio.BlogApi.models.dtos.custom;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailTemplate {

    private String content;
    private Date createdDate;
    private String subject;
    private String mailTo;

}
