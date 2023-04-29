package com.lio.BlogApi.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "blog_comments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "view_id", unique = true)
    private String viewId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    @Column(name = "reply_count")
    private Integer replyCount;

    @Column(name = "is_delete")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private BlogComment parentComment;

    /*
     * edited for commentedAccount and blog
     */
    @ManyToOne
    @JoinColumn( name = "commented_account_id")
    private Account commentedAccount;

    @ManyToOne
    @JoinColumn( name = "blog_id" )
    private Blog blog;

}
