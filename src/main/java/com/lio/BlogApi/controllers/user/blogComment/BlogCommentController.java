package com.lio.BlogApi.controllers.user.blogComment;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.services.user.blogComment.BlogCommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/v1" )
@AllArgsConstructor
public class BlogCommentController extends BaseController {

    private final BlogCommentService blogCommentService;

}
