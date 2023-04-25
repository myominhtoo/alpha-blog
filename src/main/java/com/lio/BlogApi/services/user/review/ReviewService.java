package com.lio.BlogApi.services.user.review;

import com.lio.BlogApi.models.dtos.request.review.ReviewRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.review.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    ApiResponse<?> createReview(ReviewRequestDTO reviewRequestDTO);

    ApiResponse<?> updatedReview(String reviewId , ReviewRequestDTO reviewRequestDTO);

    ReviewResponseDTO getReviewById( String reviewId );

    List<ReviewResponseDTO> getReviews( boolean isDeleted );

    /*
     * second param accountId is to check more
     * that review is of that user
     */
    ApiResponse<?> deleteReview( String reviewId , String accountId );

}
