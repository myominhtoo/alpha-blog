package com.lio.BlogApi.repositories.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.Review;

import java.util.List;
import java.util.Optional;

@Repository("reviewRepository")
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    /*
     * viewId means random generated id by app for using in public as id
     */
    Optional<Review> findByViewId( String viewId );

    Optional<Review> findByViewIdAndIsDeleteFalse(String viewId );

    List<Review> findAllByIsDeleteTrue();

    List<Review> findAllByIsDeleteFalse();

}
