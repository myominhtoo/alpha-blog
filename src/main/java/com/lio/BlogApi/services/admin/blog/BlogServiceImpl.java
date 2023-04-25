package com.lio.BlogApi.services.admin.blog;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lio.BlogApi.models.dtos.request.blog.BlogRequestDTO;
import com.lio.BlogApi.models.dtos.request.blog.BlogUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.blog.BlogResponseDTO;
import com.lio.BlogApi.models.dtos.response.category.CategoryResponseDTO;
import com.lio.BlogApi.models.entities.Blog;
import com.lio.BlogApi.models.entities.Category;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.blog.BlogRepository;
import com.lio.BlogApi.services.admin.category.CategoryService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.FileUtil;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import com.lio.BlogApi.utils.TextUtil;

import static com.lio.BlogApi.models.constants.Index.BLOG_IMAGE_PATH;

import lombok.AllArgsConstructor;

@Service("blogService")
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepo;
    private final CategoryService categoryService;

    @Override
    @Transactional
    public ApiResponse<?> createNewBlog(BlogRequestDTO blogRequestDTO, String imageName) {
        Category savedCategory = this.categoryService
                .getCategoryById(blogRequestDTO.getCategoryId());

        if (savedCategory == null)
            return this.getInvalidResponse();

        Blog newBlog = this.getBlogObject(blogRequestDTO);
        newBlog.setCoverImageName(imageName);
        newBlog.setCategory(savedCategory);

        newBlog = this.blogRepo.save(newBlog);
        /*
         * logic about sending notifications
         */

        return this.getSuccessResponse(Message.CREATE_BLOG_SUCCESS.value(), this.getBlogResponseDTO(newBlog));
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteBlog(String blogId, boolean isDelete) {
        Optional<Blog> savedBlog$ = this.blogRepo.findByViewId(blogId);

        if (savedBlog$.isEmpty()) {
            return ResponseUtil.errorResponse(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.INVALID_REQUEST.value(),
                    ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value()));
        }

        Blog savedBlog = savedBlog$.get();
        savedBlog.setDelete(isDelete);
        savedBlog.setUpdateDate(LocalDateTime.now());

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                isDelete ? Message.DELETE_BLOG_SUCCESS.value() : Message.UNDELETE_BLOG_SUCCESS.value(),
                null);
    }

    @Override
    @Transactional
    public ApiResponse<?> updateBlog(String blogId, BlogUpdateRequestDTO blogRequestDTO, String imageName) {
        Optional<Blog> savedBlog$ = this.blogRepo.findByViewId(blogId);

        if (savedBlog$.isEmpty()) {
            return this.getInvalidResponse();
        }

        Optional<Blog> searchByKeyword$ = this.blogRepo
                .findByKeywordContaining(TextUtil.makeKeyword(blogRequestDTO.getTitle()));

        if (searchByKeyword$.isPresent() && !searchByKeyword$.get().getViewId().equals(blogId)) {
            return this.getValidationFailResponse();
        }

        Blog savedBlog = savedBlog$.get();
        savedBlog.setUpdateDate(LocalDateTime.now());
        savedBlog.setContent(blogRequestDTO.getContent());
        savedBlog.setTitle(blogRequestDTO.getTitle());

        if (imageName != null)
            savedBlog.setCoverImageName(imageName);

        savedBlog = this.blogRepo.save(savedBlog);

        return this.getSuccessResponse(Message.UPDATE_BLOG_SUCCESS.value(), this.getBlogResponseDTO(savedBlog));
    }

    @Override
    public ApiResponse<?> validateCreateBlog(BlogRequestDTO blogRequestDTO) {

        Map<String, String> errorMap = new HashMap<>();

        // if( blogRequestDTO.equals(errorMap)){

        // }

        if (this.isTitleDuplicate(blogRequestDTO.getTitle())) {
            return this.getValidationFailResponse();
        }
        return null;
    }

    @Override
    public boolean isTitleDuplicate(String title) {
        Optional<Blog> blog$ = this.blogRepo.findByKeywordContaining(TextUtil.makeKeyword(title));
        return blog$.isPresent();
    }

    @Override
    public String uploadImage(MultipartFile file) throws IOException {

        if (!FileUtil.isValidFile(file))
            return null;

        String fileName = GeneratorUtil.generateImageName(
                Prefix.BLOG_IMAGE.value(),
                ViewId.BLOG_IMAGE.bound(),
                ".jpg");

        if (FileUtil.uploadFile(file, fileName, BLOG_IMAGE_PATH))
            return fileName;

        return null;

    }

    @Override
    public ApiResponse<?> getBlogDetail(String blogId) {
        if (blogId == null)
            return this.getInvalidResponse();

        Optional<Blog> blog$ = this.blogRepo.findByViewId(blogId);

        if (blog$.isEmpty())
            return this.getInvalidResponse();

        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                Message.SUCCESS.value(),
                this.getBlogResponseDTO(blog$.get()));
    }

    @Override
    public ApiResponse<?> getUploadFailedResponse() {
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.UPLOAD_FAILED.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.UPLOAD_FAILED.value()));
    }

    @Override
    public BlogUpdateRequestDTO getUpdateDTOFromResponseDTO(BlogResponseDTO blogResponseDTO) {
        BlogUpdateRequestDTO blogUpdateRequestDTO = new BlogUpdateRequestDTO();
        blogUpdateRequestDTO.setTitle(blogResponseDTO.getTitle());
        blogUpdateRequestDTO.setContent(blogResponseDTO.getContent());
        blogUpdateRequestDTO.setCategoryId(blogResponseDTO.getCategory().getCategoryId());

        return blogUpdateRequestDTO;
    }

    /*
     * for creating object
     */
    public Blog getBlogObject(BlogRequestDTO blogRequestDTO) {
        return Blog.builder()
                .title(blogRequestDTO.getTitle())
                .content(blogRequestDTO.getContent())
                .keyword(TextUtil.makeKeyword(blogRequestDTO.getTitle()))
                .createdDate(LocalDateTime.now())
                .commentCount(0)
                .reactionCount(0)
                .viewId(GeneratorUtil.generateId(Prefix.BLOG.value(), ViewId.BLOG.bound()))
                .build();
    }

    private ApiResponse<?> getInvalidResponse() {
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.INVALID_REQUEST.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.INVALID_REQUEST.value()));
    }

    private ApiResponse<?> getValidationFailResponse() {
        return ResponseUtil.errorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                Message.DUPLICATE_BLOG_TITLE.value(),
                ErrorMapUtil.getErrorMapFromValue(Message.DUPLICATE_BLOG_TITLE.value()));
    }

    private ApiResponse<?> getSuccessResponse(String message, BlogResponseDTO data) {
        return ResponseUtil.response(
                HttpStatus.OK,
                HttpStatus.OK.value(),
                message, data);
    }

    private BlogResponseDTO getBlogResponseDTO(Blog blog) {
        return BlogResponseDTO.builder()
                .blogId(blog.getViewId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .createdDate(blog.getCreatedDate())
                .updatedDate(blog.getUpdateDate())
                .category(this.getCategoryResponseDTO(blog.getCategory()))
                .imageName(blog.getCoverImageName())
                .build();
    }

    private CategoryResponseDTO getCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .categoryId(category.getViewId())
                .categoryName(category.getCategoryName())
                .createdDate(category.getCreatedDate())
                .imageName(category.getCoverImageName())
                .updatedDate(category.getUpdatedDate())
                .build();
    }
    /*
     * end here
     */

}
