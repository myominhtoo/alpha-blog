package com.lio.BlogApi.controllers.admin.category;

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
import org.springframework.web.bind.annotation.RestController;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.models.dtos.request.CategoryRequestDTO;
import com.lio.BlogApi.models.dtos.request.CategoryUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.admin.category.CategoryService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    @PostMapping(value = "${api.categories}")
    public ResponseEntity<ApiResponse<?>> createNewCategory(
            @Valid @RequestBody CategoryRequestDTO categoryRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ResponseUtil.errorResponse(
                            HttpStatus.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST.value(),
                            Message.INVALID_REQUEST_BODY.value(),
                            ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST_BODY.value())));
        }

        ApiResponse<?> categoryCreateResponse = this.categoryService.createNewCategory(categoryRequestDTO);

        return new ResponseEntity<ApiResponse<?>>(categoryCreateResponse, categoryCreateResponse.getStatus());
    }

    @PutMapping(value = "${api.categories.detail}")
    public ResponseEntity<ApiResponse<?>> updateCategory(
            @Valid @RequestBody CategoryUpdateRequestDTO categoryUpdateRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST_BODY.value(),
                                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST_BODY.value())));
        }
        ApiResponse<?> categoryUpdateResponse = this.categoryService.updateCategory(categoryUpdateRequestDTO);

        return new ResponseEntity<ApiResponse<?>>(categoryUpdateResponse, categoryUpdateResponse.getStatus());
    }

    @DeleteMapping(value = "${api.categories.detail}")
    public ResponseEntity<ApiResponse<?>> deleteCategory(
            @PathVariable(value = "categoryId", required = true) String categoryId) {
        ApiResponse<?> categoryDeleteResponse = this.categoryService.deleteCategory(categoryId, true);
        return new ResponseEntity<ApiResponse<?>>(categoryDeleteResponse, categoryDeleteResponse.getStatus());
    }

    @PostMapping(value = "${api.categories.detail}")
    public ResponseEntity<ApiResponse<?>> undeleteCategory(
            @PathVariable(value = "categoryId", required = true) String categoryId) {
        ApiResponse<?> undeleteCategoryResponse = this.categoryService.undeleteCategory(categoryId);
        return new ResponseEntity<ApiResponse<?>>(undeleteCategoryResponse, undeleteCategoryResponse.getStatus());
    }

    @GetMapping(value = "${api.categories}")
    public ResponseEntity<ApiResponse<?>> getAllCategories() {
        return ResponseEntity.ok()
                .body(ResponseUtil.response(
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Message.SUCCESS.value(),
                        this.categoryService.getAllCategories()));
    }
}
