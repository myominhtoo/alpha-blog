package com.lio.BlogApi.services.admin.blog;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.lio.BlogApi.models.dtos.request.BlogRequestDTO;
import com.lio.BlogApi.models.dtos.request.BlogUpdateRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.BlogResponseDTO;

public interface BlogService {

    ApiResponse<?> createNewBlog(BlogRequestDTO blogRequestDTO, String imageName);

    ApiResponse<?> updateBlog(String blogId, BlogUpdateRequestDTO blogRequestDTO, String imageName);

    /*
     * isDelete is for will delete or not
     * i.e if true will set delete status to true
     * if not true will do opposite
     */
    ApiResponse<?> deleteBlog(String blogId, boolean isDelete);

    ApiResponse<?> validateCreateBlog(BlogRequestDTO blogRequestDTO);

    ApiResponse<?> getBlogDetail(String blogId);

    boolean isTitleDuplicate(String title);

    /*
     * will return uploaded file name back
     * will return null if something went wrong
     */
    String uploadImage(MultipartFile file) throws IOException;

    /*
     * just for Response DTO
     */
    ApiResponse<?> getUploadFailedResponse();

    BlogUpdateRequestDTO getUpdateDTOFromResponseDTO(BlogResponseDTO blogResponseDTO);
    /*
     * end here
     */

}
