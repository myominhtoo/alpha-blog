package com.lio.BlogApi.services.user.subscriber;

import com.lio.BlogApi.models.dtos.request.subscriber.SubscriberRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.entities.Subscriber;

public interface SubscriberService {

    Subscriber subscribe(SubscriberRequestDTO requestDTO);

    boolean unsubscribe(String subscriptionId);

    ApiResponse<?> validateSubscriber(SubscriberRequestDTO requestDTO);

}
