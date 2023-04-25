package com.lio.BlogApi.models.dtos.response.blog;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lio.BlogApi.models.dtos.response.category.CategoryResponseDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogResponseDTO {

    private String blogId;

    private String title;

    private String content;

    private String imageName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedDate;

    private CategoryResponseDTO category;

}
