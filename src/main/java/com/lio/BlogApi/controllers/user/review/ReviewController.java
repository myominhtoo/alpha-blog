package com.lio.BlogApi.controllers.user.review;

import com.lio.BlogApi.models.dtos.request.review.ReviewRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.review.ReviewResponseDTO;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.user.review.ReviewService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.lio.BlogApi.controllers.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class ReviewController extends BaseController {

    private final ReviewService reviewService;

    /*
    * TO DO
    * (1) write create review
    * (2) write update review
    * (3) delete review
    * (4) select review
    * (5) select reviews
    */

    /*
     * create new review => /api/v1/reviews
     */
    @PostMapping( value = "${api.reviews}" )
    public ResponseEntity<ApiResponse<?>> createReview(
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO ,
            BindingResult bindingResult
    ){
        if( bindingResult.hasErrors() ){
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.errorResponse(
                            HttpStatus.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST.value(),
                            Message.INVALID_REQUEST_BODY.value(),
                            ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)
                    ));
        }

        ApiResponse<?> createReviewResponse = this.reviewService.createReview(reviewRequestDTO);

        return new ResponseEntity<ApiResponse<?>>( createReviewResponse , createReviewResponse.getStatus() );
    }


    /*
     * update review => /api/v1/reviews/{reviewId}
     */
    @PutMapping( value = "${api.reviews.detail}")
    public ResponseEntity<ApiResponse<?>> updateReview(
            @PathVariable(value = "reviewId" , required = true) String reviewId,
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO ,
            BindingResult bindingResult
    ){
        if( bindingResult.hasErrors() ){
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.errorResponse(
                            HttpStatus.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST.value(),
                            Message.INVALID_REQUEST_BODY.value(),
                            ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)
                    ));
        }
        ApiResponse<?> updateResponse = this.reviewService.updatedReview( reviewId, reviewRequestDTO);

        return new ResponseEntity<ApiResponse<?>>(
                updateResponse ,
                updateResponse.getStatus()
        );
    }

    /*
     * Getting all reviews of app => /api/v1/reviews
     */
    @GetMapping( value = "${api.reviews}")
    public ResponseEntity<ApiResponse<?>> getAllReviews(){
        return ResponseEntity.ok()
                .body(
                        ResponseUtil.response(
                                HttpStatus.OK,
                                HttpStatus.OK.value(),
                                Message.SUCCESS.value(),
                                this.reviewService.getReviews(false)
                        )
                );
    }

    /*
     * Get target review from reviews => /api/v1/reviews/{reviewId}
     */
    @GetMapping( value = "${api.reviews.detail}")
    public ResponseEntity<ApiResponse<?>> getReviewById(
            @PathVariable( value = "reviewId", required = true ) String reviewId
    ){
        ReviewResponseDTO reviewResponseDTO = this.reviewService.getReviewById(reviewId);

        HttpStatus httpStatus = reviewResponseDTO == null
                                ? HttpStatus.BAD_REQUEST
                                : HttpStatus.OK;
        ApiResponse<?> apiResponse = reviewResponseDTO == null
                                     ? (
                                         ResponseUtil.errorResponse(
                                                 httpStatus,
                                                 httpStatus.value(),
                                                 Message.INVALID_REQUEST.value(),
                                                 ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value())
                                         )    )
                                     : (
                                         ResponseUtil.response(
                                                 HttpStatus.OK,
                                                 HttpStatus.OK.value(),
                                                 Message.SUCCESS.value(),
                                                 reviewResponseDTO
                                         )
                                     );

        return new ResponseEntity<ApiResponse<?>>(
                apiResponse,
                apiResponse.getStatus()
        );
    }


    /*
     * will implement with email from authentication later
     */
    @DeleteMapping( value = "${api.reviews.detail}")
    public ResponseEntity<ApiResponse<?>> deleteReview(
            @PathVariable( value = "reviewId" , required = true ) String reviewId ,
            HttpServletRequest request
    ){
        ApiResponse<?> apiResponse =  this.reviewService.deleteReview(reviewId,null);
        return new ResponseEntity<ApiResponse<?>>(
                apiResponse ,
                apiResponse.getStatus()
        );
    }

}
