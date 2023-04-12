package com.lio.BlogApi.services.admin.category;

import com.lio.BlogApi.models.dtos.request.CategoryRequestDTO;
import com.lio.BlogApi.models.dtos.request.CategoryUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    ApiResponse<?> createNewCategory(CategoryRequestDTO categoryRequestDTO);

    boolean isDuplicateCategoryName(String categoryName);

    ApiResponse<?> updateCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO);

    ApiResponse<?> deleteCategory(String categoryId, boolean isDelete);

    ApiResponse<?> undeleteCategory(String categoryId);

    List<CategoryResponseDTO> getAllCategories();

}
