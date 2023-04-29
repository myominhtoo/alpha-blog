package com.lio.BlogApi.models.dtos.request.blogComment;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BlogCommentRequestDTO {

    @NotNull( message = "{blogComment.notnull}")
    @NotEmpty( message = "{blogComment.notempty}")
    @Length( max = 300 , message = "{blogComment.notrange}")
    private String content;

    @NotNull( message = "{accountId.notnull}")
    @NotEmpty( message = "{accountId.notempty}")
    private String accountId;

    @NotNull( message = "{blogId.notnull}")
    @NotEmpty( message = "{blogId.notempty}")
    private String blogId;

    private String parentCommentId;

}
