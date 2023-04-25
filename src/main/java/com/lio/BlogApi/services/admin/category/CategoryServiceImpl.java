package com.lio.BlogApi.services.admin.category;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lio.BlogApi.models.dtos.request.category.CategoryRequestDTO;
import com.lio.BlogApi.models.dtos.request.category.CategoryUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.category.CategoryResponseDTO;
import com.lio.BlogApi.models.entities.Category;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.category.CategoryRepository;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.FileUtil;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import com.lio.BlogApi.utils.TextUtil;
import static com.lio.BlogApi.models.constants.Index.CATEGORY_IMAGE_PATH;

import lombok.AllArgsConstructor;

@Service("categoryService")
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    @Override
    @Transactional
    public ApiResponse<?> createNewCategory(CategoryRequestDTO categoryRequestDTO) {
        if (this.isDuplicateCategoryName(categoryRequestDTO.getCategoryName())) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.DUPLICATE_CATEGORY_NAME.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.DUPLICATE_CATEGORY_NAME.value()));
        }

        Category createdCategory = this.categoryRepo
                .save(this.getNewCategoryObject(categoryRequestDTO.getCategoryName()));

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.CREATE_CATEGORY_SUCCESS.value(),
                getCategoryResponse(createdCategory));
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteCategory(String categoryId, boolean isDelete) {
        Optional<Category> savedCategory$ = this.categoryRepo.findByViewId(categoryId);
        if (savedCategory$.isEmpty()) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.INVALID_REQUEST.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value()));
        }
        Category savedCategory = savedCategory$.get();
        savedCategory.setUpdatedDate(LocalDateTime.now());
        savedCategory.setDelete(isDelete);

        this.categoryRepo.save(savedCategory);

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                isDelete ? Message.DELETE_CATEGORY_SUCCESS.value() : Message.UNDELETE_CATEGORY_SUCCESS.value(),
                null);
    }

    @Override
    public ApiResponse<?> undeleteCategory(String categoryId) {
        return this.deleteCategory(categoryId, false);
    }

    @Override
    public boolean isDuplicateCategoryName(String categoryName) {
        return this.categoryRepo.findByCategoryNameContaining(categoryName)
                .isPresent();
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return getCategoryResponse(this.categoryRepo.findAll());
    }

    @Override
    public ApiResponse<?> uploadImage(MultipartFile file) throws IOException {
        if (!FileUtil.isValidFile(file)) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.INVALID_FILE_FORMAT.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_FILE_FORMAT.value()));
        }

        String fileName = GeneratorUtil.generateImageName(
                Prefix.CATEGORY_IMAGE.value(),
                ViewId.CATEGORY_IMAGE.bound(),
                ".jpg");

        boolean uploadStatus = FileUtil.uploadFile(
                file,
                fileName,
                CATEGORY_IMAGE_PATH);

        if (!uploadStatus) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.UPLOAD_FAILED.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.UPLOAD_FAILED.value()));
        }

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.UPLOAD_SUCCESS.value(),
                fileName);
    }

    @Override
    public ApiResponse<?> updateCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO, String imageName) {
        Optional<Category> category$ = this.categoryRepo.findByViewId(categoryUpdateRequestDTO.getCategoryId());

        if (category$.isEmpty()) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.INVALID_REQUEST.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value()));
        }
        Category category = category$.get();
        category.setUpdatedDate(LocalDateTime.now());
        category.setCategoryName(categoryUpdateRequestDTO.getCategoryName());
        category.setKeyword(TextUtil.makeKeyword(category.getCategoryName()));

        if (imageName != null)
            category.setCoverImageName(imageName);

        this.categoryRepo.save(category);
        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.UPDATE_CATEGORY_SUCCESS.value(),
                getCategoryResponse(category));
    }

    @Override
    public Category getCategoryById(String categoryId) {
        Optional<Category> category$ = this.categoryRepo.findByViewId(categoryId);
        return category$.isPresent() ? category$.get() : null;
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        Optional<Category> category$ = this.categoryRepo.findById(categoryId);
        return category$.isPresent() ? category$.get() : null;
    }

    /*
     * just for creating object
     */
    private Category getNewCategoryObject(String categoryName) {
        return Category.builder()
                .categoryName(categoryName)
                .keyword(TextUtil.makeKeyword(categoryName))
                .createdDate(LocalDateTime.now())
                .isDelete(false)
                .viewId(GeneratorUtil.generateId(Prefix.CATEGORY.value(), ViewId.CATEGORY.bound()))
                .build();
    }

    private CategoryResponseDTO getCategoryResponse(Category category) {
        return CategoryResponseDTO.builder()
                .categoryId(category.getViewId())
                .categoryName(category.getCategoryName())
                .imageName(category.getCoverImageName())
                .createdDate(category.getCreatedDate())
                .updatedDate(category.getUpdatedDate())
                .keyword(category.getKeyword())
                .build();
    }

    private List<CategoryResponseDTO> getCategoryResponse(List<Category> categories) {
        return categories.stream()
                .map(category -> {
                    return getCategoryResponse(category);
                }).toList();
    }
    /*
     * end here
     */
}
