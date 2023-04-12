package com.lio.BlogApi.repositories.subscriber;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.Subscriber;

@Repository("subscriberRepository")
public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {

    Optional<Subscriber> findByEmail(String email);

    Optional<Subscriber> findByViewId(String viewId);

}
