package com.lio.BlogApi.controllers.user.blogComment;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.models.dtos.request.blogComment.BlogCommentRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.user.blogComment.BlogCommentService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping( value = "/v1" )
@AllArgsConstructor
public class BlogCommentController extends BaseController {

    private final BlogCommentService blogCommentService;

    /*
     * for creating comments => /api/v1/comments
     */
    @PostMapping( value = "${api.comments}")
    public ResponseEntity<ApiResponse<?>> createComment(
            @Valid @RequestBody BlogCommentRequestDTO blogCommentRequestDTO ,
            BindingResult bindingResult
    ){
        if( bindingResult.hasErrors() ){
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.badRequest(
                            Message.INVALID_REQUEST_BODY.value() ,
                            ErrorMapUtil.getErrorMapFromBindingResult(bindingResult))
                    );
        }

        ApiResponse<?> commentCreateResponse = this.blogCommentService.createBlogComment(blogCommentRequestDTO);

        return new ResponseEntity<ApiResponse<?>>(
                commentCreateResponse ,
                commentCreateResponse.getStatus()
        );
    }

    @PutMapping( value = "${api.comments.detail}")
    public ResponseEntity<ApiResponse<?>> updateComment(
            @PathVariable( value = "commentId" ) String commentId ,
            @Valid @RequestBody BlogCommentRequestDTO blogCommentRequestDTO ,
            BindingResult bindingResult
    ){
        if( bindingResult.hasErrors() || commentId == null ){
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.badRequest(
                            Message.INVALID_REQUEST_BODY.value() ,
                            ErrorMapUtil.getErrorMapFromBindingResult(bindingResult))
                    );
        }

        ApiResponse<?> updateCommentResponse = this.blogCommentService
                .updateBlogComment( commentId , blogCommentRequestDTO );

        return new ResponseEntity<ApiResponse<?>>(
                updateCommentResponse ,
                updateCommentResponse.getStatus()
        );
    }

    @DeleteMapping( value = "${api.comments.detail}")
    public ResponseEntity<ApiResponse<?>> deleteComment(
            @PathVariable( value = "commentId") String commentId
    ){
        if( commentId == null )
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.invalidRequest());

        ApiResponse<?> deleteResponse = this.blogCommentService.deleteComment( commentId , true );

        return new ResponseEntity<ApiResponse<?>>(
                deleteResponse ,
                deleteResponse.getStatus()
        );
    }

    @GetMapping( value = "${api.comments.detail}")
    public ResponseEntity<ApiResponse<?>> getCommentDetail(
            @PathVariable( value = "commentId") String commentId
    ){
        if( commentId == null )
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.invalidRequest());

        ApiResponse<?> apiResponse =  ResponseUtil.success(Message.SUCCESS.value() , this.blogCommentService.getCommentById(commentId));
        return new ResponseEntity<ApiResponse<?>>(
                apiResponse ,
                apiResponse.getStatus()
        );
    }

    @GetMapping( value = "${api.comments}")
    public ResponseEntity<ApiResponse<?>> getComments(
            @RequestParam( value = "blogId" , required = true ) String blogId
    ){
        if( blogId == null ) {
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.badRequest(
                            Message.INVALID_REQUEST.value(),
                            ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value()))
                    );
        }

        ApiResponse<?> apiResponse = ResponseUtil.success(
                Message.SUCCESS.value() ,
                this.blogCommentService.getCommentsByBlogId(blogId)
        );
        return ResponseEntity.ok()
                .body(apiResponse);
    }

}
