package com.lio.BlogApi.models.dtos.response.blogComment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogCommentResponseDTO {

    private String commentId;
    private String content;
    private String commentedAccountId;
    private Integer replyCount;

    @JsonFormat( pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime createdDate;

    @JsonFormat( pattern = "yyyy-MM-dd hh:MM:ss")
    private LocalDateTime updatedDate;
    private String parentCommentId;
    private String blogId;

}
