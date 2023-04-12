package com.lio.BlogApi.repositories.category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.Category;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryNameContaining(String categoryName);

    Optional<Category> findByViewId(String viewId);

}
