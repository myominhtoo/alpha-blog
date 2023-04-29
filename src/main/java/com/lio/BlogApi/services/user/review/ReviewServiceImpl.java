package com.lio.BlogApi.services.user.review;

import com.lio.BlogApi.models.dtos.request.review.ReviewRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.review.ReviewResponseDTO;
import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.Review;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.account.AccountRepository;
import com.lio.BlogApi.repositories.review.ReviewRepository;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("reviewService")
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepo;
    private final AccountRepository accountRepo;

    @Override
    @Transactional
    public ApiResponse<?> createReview(ReviewRequestDTO reviewRequestDTO) {

        Optional<Account> savedAccount$ = this.accountRepo
                        .findByViewIdAndIsDeleteFalse(reviewRequestDTO.getAccountId());

        if( savedAccount$.isEmpty() )
            return this.getInvalidBodyResponse();

        Review review = Review.builder()
                        .content(reviewRequestDTO.getContent())
                        .account(savedAccount$.get())
                        .viewId(GeneratorUtil.generateId(Prefix.REVIEW.value(), ViewId.REVIEW.bound()))
                        .createdDate(LocalDateTime.now())
                        .isDelete(false)
                        .build();

        this.reviewRepo.save(review);

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.CREATE_REVIEW_SUCCESS.value(),
                this.getReviewResponseFromReview(review)
        );
    }

    @Override
    @Transactional
    public ApiResponse<?> updatedReview( String reviewId , ReviewRequestDTO reviewRequestDTO) {
        Optional<Account> savedAccount$ = this.accountRepo
                    .findByViewIdAndIsDeleteFalse(reviewRequestDTO.getAccountId());

        if( savedAccount$.isEmpty())
            return this.getInvalidBodyResponse();


        Optional<Review> savedReview$ = this.reviewRepo.findByViewIdAndIsDeleteFalse(reviewId);

        if( savedReview$.isEmpty() && !savedReview$.get().getAccount().getViewId().equals(reviewRequestDTO.getAccountId()))
            return this.getInvalidBodyResponse();

        Review savedReview = savedReview$.get();
        savedReview.setContent(reviewRequestDTO.getContent());
        savedReview.setUpdatedDate(LocalDateTime.now());

        this.reviewRepo.save(savedReview);

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.UPDATE_REVIEW_SUCCESS.value(),
                this.getReviewResponseFromReview(savedReview)
        );
    }

    @Override
    public ReviewResponseDTO getReviewById(String reviewId) {
        if( reviewId == null )
            return null;

        Optional<Review> savedReview$ = this.reviewRepo.findByViewId(reviewId);

        return savedReview$
                .map(this::getReviewResponseFromReview)
                .orElse(null);
    }

    @Override
    public List<ReviewResponseDTO> getReviews( boolean isDeleted ) {
        List<Review> savedReviews = isDeleted
                                    ? this.reviewRepo.findAllByIsDeleteTrue()
                                    : this.reviewRepo.findAllByIsDeleteFalse();
        return this.getReviewsResponsesFromReviews(savedReviews);
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteReview(String reviewId, String accountId) {
        Optional<Review> savedReview$ = this.reviewRepo.findByViewIdAndIsDeleteFalse(reviewId);

        if( savedReview$.isEmpty())
            return this.getInvalidBodyResponse();

        Review savedReview = savedReview$.get();

        if( !savedReview.getAccount().getViewId().equals(accountId))
            return this.getInvalidBodyResponse();

        savedReview.setDelete(true);
        savedReview.setUpdatedDate(LocalDateTime.now());

        this.reviewRepo.save(savedReview);

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.DELETE_REVIEW_SUCCESS.value(),
                null
        );
    }

    /*
     * for creating objects
     */
     private ReviewResponseDTO getReviewResponseFromReview( Review review ){
         return ReviewResponseDTO.builder()
                 .accountId(review.getAccount().getViewId())
                 .accountName(review.getAccount().getUsername())
                 .content(review.getContent())
                 .reviewId(review.getViewId())
                 .createdDate(review.getCreatedDate())
                 .updatedDate(review.getUpdatedDate())
                 .build();
     }

     private List<ReviewResponseDTO> getReviewsResponsesFromReviews( List<Review> reviews ){
         return reviews.stream()
                 .map(this::getReviewResponseFromReview)
                 .collect(Collectors.toList());
     }

     private ApiResponse<?> getInvalidBodyResponse(){
         return ResponseUtil.errorResponse(
                 HttpStatus.BAD_REQUEST,
                 HttpStatus.BAD_REQUEST.value(),
                 Message.INVALID_REQUEST_BODY.value(),
                 ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST_BODY.value())
         );
     }
    /*
     * end here
     */
}