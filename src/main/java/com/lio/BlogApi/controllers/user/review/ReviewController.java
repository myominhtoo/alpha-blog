package com.lio.BlogApi.controllers.user.review;

import com.lio.BlogApi.services.user.review.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lio.BlogApi.controllers.BaseController;

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


}
