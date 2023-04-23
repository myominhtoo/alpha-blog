package com.lio.BlogApi.services.admin.category;

import com.lio.BlogApi.models.dtos.request.CategoryRequestDTO;
import com.lio.BlogApi.models.dtos.request.CategoryUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.CategoryResponseDTO;
import com.lio.BlogApi.models.entities.Category;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CategoryService {

    ApiResponse<?> createNewCategory(CategoryRequestDTO categoryRequestDTO);

    boolean isDuplicateCategoryName(String categoryName);

    ApiResponse<?> updateCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO, String imageName);

    ApiResponse<?> deleteCategory(String categoryId, boolean isDelete);

    ApiResponse<?> undeleteCategory(String categoryId);

    Category getCategoryById(String categoryId);

    Category getCategoryById(Integer categoryId);

    List<CategoryResponseDTO> getAllCategories();

    ApiResponse<?> uploadImage(MultipartFile file) throws IOException;

}
