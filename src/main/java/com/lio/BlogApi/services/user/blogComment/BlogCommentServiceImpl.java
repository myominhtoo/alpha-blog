package com.lio.BlogApi.services.user.blogComment;

import com.lio.BlogApi.models.dtos.request.blogComment.BlogCommentRequestDTO;
import com.lio.BlogApi.models.dtos.response.blogComment.BlogCommentResponseDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.Blog;
import com.lio.BlogApi.models.entities.BlogComment;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.account.AccountRepository;
import com.lio.BlogApi.repositories.blog.BlogRepository;
import com.lio.BlogApi.repositories.blogComment.BlogCommentRepository;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("blogCommentService")
@AllArgsConstructor
public class BlogCommentServiceImpl implements  BlogCommentService {

    private final BlogCommentRepository blogCommentRepo;
    private final BlogRepository blogRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ApiResponse<?> createBlogComment(BlogCommentRequestDTO blogCommentRequestDTO) {
        /*
         * for checking accountId is present as user or not
         */
        Optional<Account> savedAccount$ = this.accountRepository
                .findByViewId(blogCommentRequestDTO.getAccountId());

        /*
         * for checking blogId is present as blog or not
         */
        Optional<Blog> savedBlog$ = this.blogRepository
                .findByViewId(blogCommentRequestDTO.getBlogId());

        /*
         * if parent comment Id is present and will need to check that comment really have or not
         * and parent comment is possible null when current comment is root comment
         */
        Optional<BlogComment> parentComment$ = this.blogCommentRepo
                .findByViewId(
                        blogCommentRequestDTO.getParentCommentId() == null
                                ? "null"
                                : blogCommentRequestDTO.getParentCommentId()
                );

        if( savedAccount$.isEmpty() || savedBlog$.isEmpty() ||
                ( blogCommentRequestDTO.getParentCommentId() != null && parentComment$.isEmpty() ) )
            return ResponseUtil.invalidRequestBody();

        BlogComment blogComment = this
                .getBlogCommentEntityFromRequestDTO(
                        blogCommentRequestDTO ,
                        parentComment$.orElse(null),
                        savedBlog$.get(),
                        savedAccount$.get()
                );

        this.blogCommentRepo.save(blogComment);

        if( blogComment.getParentComment() != null ){
            BlogComment parentComment = blogComment.getParentComment();
            parentComment.setReplyCount(
                    parentComment.getReplyCount() == null ? 1  : parentComment.getReplyCount() + 1
            );
            parentComment.setUpdatedDate(LocalDateTime.now());
            this.blogCommentRepo.save(parentComment);
        }

        return ResponseUtil.success(
                Message.CREATE_COMMENT_SUCCESS.value() ,
                this.getBlogCommentResponseFromEntity(blogComment)
        );
    }

    @Override
    @Transactional
    public ApiResponse<?> updateBlogComment(String commentId, BlogCommentRequestDTO blogCommentRequestDTO) {
        Optional<BlogComment> blogComment$ = this.blogCommentRepo
                .findByViewId(commentId);

        BlogComment blogComment = blogComment$.orElse(null);

        if( blogComment$.isEmpty() ||
                !blogComment.getBlog().getViewId().equals(blogCommentRequestDTO.getBlogId()) ||
                !blogComment.getCommentedAccount().getViewId().equals(blogCommentRequestDTO.getAccountId())
        )
            return ResponseUtil.invalidRequestBody();

        blogComment.setContent(blogCommentRequestDTO.getContent());
        blogComment.setUpdatedDate(LocalDateTime.now());

        this.blogCommentRepo.save(blogComment);

        return ResponseUtil.success(
                Message.UPDATE_COMMENT_SUCCESS.value(),
                this.getBlogCommentResponseFromEntity(blogComment)
        );
    }

    @Override
    @Transactional
    public ApiResponse<?> deleteComment(String commentId, boolean isDelete) {
        BlogComment blogComment = this.blogCommentRepo.findByViewId(commentId)
                .orElse(null);

        if( blogComment == null )
            return ResponseUtil.invalidRequestBody();

        blogComment.setUpdatedDate(LocalDateTime.now());
        blogComment.setDelete(isDelete);

        this.blogCommentRepo.save(blogComment);

        if( blogComment.getParentComment() != null && isDelete ){
            BlogComment parentComment = blogComment.getParentComment();
            parentComment.setUpdatedDate(LocalDateTime.now());
            parentComment.setReplyCount(
                    parentComment.getReplyCount() == null
                            ? 0
                            : parentComment.getReplyCount() - 1
            );
            this.blogCommentRepo.save(parentComment);
        }

        return ResponseUtil.success( Message.DELETE_COMMENT_SUCCESS.value() );
    }

    @Override
    public BlogCommentResponseDTO getCommentById(String commentId) {
        Optional<BlogComment> savedBlogComment$ = this.blogCommentRepo
                .findByViewId(commentId);

        return savedBlogComment$
                .map(this::getBlogCommentResponseFromEntity).orElse(null);

    }

    @Override
    public List<BlogCommentResponseDTO> getCommentsByBlogId(String blogId) {
        return this.getBlogCommentResponsesFromEntities(
                this.blogCommentRepo.findByBlogViewId(blogId)
        );
    }

    /*
     * just for creating object
     */
    public BlogComment getBlogCommentEntityFromRequestDTO(
            BlogCommentRequestDTO blogCommentRequestDTO ,
            BlogComment parentComment ,
            Blog blog ,
            Account commentedAccount
    ){
        return BlogComment.builder()
                .content(blogCommentRequestDTO.getContent())
                .viewId(GeneratorUtil.generateId(Prefix.BLOG_COMMENT.value(), ViewId.BLOG_COMMENT.bound()))
                .createdDate(LocalDateTime.now())
                .replyCount(0)
                .isDelete(false)
                .blog(blog)
                .commentedAccount(commentedAccount)
                .parentComment(parentComment)
                .build();
    }

    public BlogCommentResponseDTO getBlogCommentResponseFromEntity( BlogComment blogComment ){
        return BlogCommentResponseDTO.builder()
                .commentId(blogComment.getViewId())
                .content(blogComment.getContent())
                .createdDate(blogComment.getCreatedDate())
                .updatedDate(blogComment.getUpdatedDate())
                .replyCount(blogComment.getReplyCount())
                .blogId(blogComment.getBlog().getViewId())
                .parentCommentId(blogComment.getParentComment() == null ? null : blogComment.getParentComment().getViewId())
                .commentedAccountId(blogComment.getCommentedAccount().getViewId())
                .build();
    }

    public List<BlogCommentResponseDTO> getBlogCommentResponsesFromEntities( List<BlogComment> blogComments ){
        return blogComments.stream()
                .map(this::getBlogCommentResponseFromEntity)
                .collect(Collectors.toList());
    }
    /*
     * end here
     */

}
