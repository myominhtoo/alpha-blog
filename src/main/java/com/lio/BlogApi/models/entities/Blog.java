package com.lio.BlogApi.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "blogs")
@Data
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "view_id", unique = true)
    private String viewId;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "keyword", unique = true)
    private String keyword;

    @Column(name = "content")
    private String content;

    @Column(name = "comment_count")
    private Integer commentCount;

    @Column(name = "reaction_count")
    private Integer reactionCount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    @Column(name = "cover_image_name", unique = true)
    private String coverImageName;

}
