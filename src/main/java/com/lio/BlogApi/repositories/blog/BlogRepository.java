package com.lio.BlogApi.repositories.blog;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.Blog;

@Repository("blogRepository")
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Optional<Blog> findByKeywordContaining(String keyword);

    Optional<Blog> findByViewId(String viewId);

}
