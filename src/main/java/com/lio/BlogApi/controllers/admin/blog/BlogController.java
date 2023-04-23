package com.lio.BlogApi.controllers.admin.blog;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.models.dtos.request.BlogRequestDTO;
import com.lio.BlogApi.models.dtos.request.BlogUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.BlogResponseDTO;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.admin.blog.BlogService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class BlogController extends BaseController {

    private final BlogService blogService;

    @PostMapping(value = "${api.blogs}")
    public ResponseEntity<ApiResponse<?>> createNewBlog(
            @Valid @RequestBody BlogRequestDTO blogRequestDTO,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST_BODY.value(),
                                    ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)));
        }

        ApiResponse<?> validationResponse = this.blogService.validateCreateBlog(blogRequestDTO);

        if (validationResponse != null)
            return ResponseEntity.badRequest()
                    .body(validationResponse);

        ApiResponse<?> createBlogResponse = this.blogService.createNewBlog(blogRequestDTO, null);

        return new ResponseEntity<ApiResponse<?>>(createBlogResponse, createBlogResponse.getStatus());
    }

    @DeleteMapping(value = "${api.blogs.detail}")
    public ResponseEntity<ApiResponse<?>> deleteBlog(
            @PathVariable(value = "blogId", required = true) String blogId) {
        ApiResponse<?> deleteBlogResponse = this.blogService.deleteBlog(blogId, true);
        return new ResponseEntity<ApiResponse<?>>(deleteBlogResponse, deleteBlogResponse.getStatus());
    }

    @PutMapping(value = "${api.blogs.detail.restore}")
    public ResponseEntity<ApiResponse<?>> restoreBlog(
            @PathVariable(value = "blogId", required = true) String blogId) {
        ApiResponse<?> blogRestoreResponse = this.blogService.deleteBlog(blogId, false);
        return new ResponseEntity<ApiResponse<?>>(blogRestoreResponse, blogRestoreResponse.getStatus());
    }

    @PutMapping(value = "${api.blogs.detail}")
    public ResponseEntity<ApiResponse<?>> updateBlog(
            @PathVariable(value = "blogId", required = true) String blogId,
            @Valid @RequestBody BlogUpdateRequestDTO blogUpdateRequestDTO,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST_BODY.value(),
                                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST_BODY.value())));
        }

        ApiResponse<?> blogUpdateResponse = this.blogService.updateBlog(blogId, blogUpdateRequestDTO, null);

        return new ResponseEntity<ApiResponse<?>>(
                blogUpdateResponse,
                blogUpdateResponse.getStatus());

    }

    @GetMapping(value = "${api.blogs.detail}")
    public ResponseEntity<ApiResponse<?>> getBlogDetail(
            @PathVariable(value = "blogId", required = true) String blogId) {
        ApiResponse<?> blogDetailResponse = this.blogService.getBlogDetail(blogId);
        return new ResponseEntity<ApiResponse<?>>(blogDetailResponse, blogDetailResponse.getStatus());
    }

    @PostMapping(value = "${api.blogs.detail.upload-image}")
    public ResponseEntity<ApiResponse<?>> uploadBlogImage(
            @PathVariable(value = "blogId", required = true) String blogId,
            @RequestParam(value = "file", required = true) MultipartFile imageFile) throws IOException {

        String fileName = this.blogService.uploadImage(imageFile);

        if (fileName == null) {
            return ResponseEntity.badRequest()
                    .body(
                            this.blogService.getUploadFailedResponse());
        }

        BlogResponseDTO savedBlog = (BlogResponseDTO) this.blogService.getBlogDetail(blogId).getData();

        if (savedBlog == null) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST.value(),
                                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value())));
        }

        BlogUpdateRequestDTO blogUpdateRequestDTO = this.blogService.getUpdateDTOFromResponseDTO(savedBlog);

        ApiResponse<?> updateResponse = this.blogService.updateBlog(blogId, blogUpdateRequestDTO, fileName);

        return new ResponseEntity<ApiResponse<?>>(updateResponse, updateResponse.getStatus());
    }

}
