package com.lio.BlogApi.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "category_name", unique = true)
    private String categoryName;

    @Column(name = "keyword", unique = true)
    private String keyword;

    @Column(name = "view_id", unique = true)
    private String viewId;

    @Column(name = "cover_image_name", unique = true, nullable = true)
    private String coverImageName;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /*
     * edited column for deleting
     */
    @Column(name = "is_delete")
    private boolean isDelete;

}
