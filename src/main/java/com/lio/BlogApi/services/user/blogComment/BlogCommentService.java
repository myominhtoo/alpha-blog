package com.lio.BlogApi.services.user.blogComment;

import com.lio.BlogApi.models.dtos.request.blogComment.BlogCommentRequestDTO;
import com.lio.BlogApi.models.dtos.response.blogComment.BlogCommentResponseDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;

import java.util.List;

public interface BlogCommentService {

    ApiResponse<?> createBlogComment(BlogCommentRequestDTO blogCommentRequestDTO);

    ApiResponse<?> updateBlogComment( String commentId , BlogCommentRequestDTO blogCommentRequestDTO );

    ApiResponse<?> deleteComment( String commentId , boolean isDelete );

    BlogCommentResponseDTO getCommentById( String commentId );

    List<BlogCommentResponseDTO> getCommentsByBlogId( String blogId );

}
