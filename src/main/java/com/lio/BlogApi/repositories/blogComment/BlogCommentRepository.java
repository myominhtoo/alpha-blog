package com.lio.BlogApi.repositories.blogComment;

import com.lio.BlogApi.models.entities.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("blogCommentRepository")
public interface BlogCommentRepository extends JpaRepository<BlogComment,Integer> {

    List<BlogComment> findByBlogViewId( String blogId );

    Optional<BlogComment> findByViewId( String blogCommentId );

}
