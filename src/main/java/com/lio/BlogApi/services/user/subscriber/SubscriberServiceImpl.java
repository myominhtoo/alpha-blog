package com.lio.BlogApi.services.user.subscriber;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.dtos.request.subscriber.SubscriberRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.entities.Subscriber;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.subscriber.SubscriberRepository;
import com.lio.BlogApi.utils.GeneratorUtil;
import com.lio.BlogApi.utils.ResponseUtil;

import lombok.AllArgsConstructor;

@Service("subscriberService")
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepo;

    @Override
    @Transactional
    public Subscriber subscribe(SubscriberRequestDTO requestDTO) {
        Optional<Subscriber> subscriber$ = this.subscriberRepo.findByEmail(requestDTO.getEmail());

        /*
         * will update subscriber is present and un-active
         */
        if (subscriber$.isPresent() && !subscriber$.get().isStatus()) {
            Subscriber subscriber = subscriber$.get();
            subscriber.setStatus(true);
            subscriber.setUpdateDate(LocalDateTime.now());
            return this.subscriberRepo.save(subscriber);
        }

        /*
         * will create brand new row if not present
         */
        Subscriber subscriber = Subscriber.builder()
                .email(requestDTO.getEmail())
                .location(requestDTO.getLocation())
                .createdDate(LocalDateTime.now())
                .status(true)
                .viewId(GeneratorUtil.generateId(Prefix.SUBSCRIBER.value(), ViewId.SUBSCRIBER.bound()))
                .build();
        return this.subscriberRepo.save(subscriber);
    }

    /*
     * will change status of subscriber to false
     */
    @Override
    @Transactional
    public boolean unsubscribe(String subscriptionId) {
        Optional<Subscriber> subscriber$ = this.subscriberRepo.findByViewId(subscriptionId);
        if (subscriber$.isEmpty())
            return false;
        Subscriber subscriber = subscriber$.get();
        subscriber.setStatus(false);
        this.subscriberRepo.save(subscriber);
        return true;
    }

    @Override
    public ApiResponse<?> validateSubscriber(SubscriberRequestDTO requestDTO) {
        Optional<Subscriber> subscriber$ = this.subscriberRepo.findByEmail(requestDTO.getEmail());
        if (subscriber$.isPresent() && subscriber$.get().isStatus())
            return ResponseUtil.response(
                    HttpStatus.BAD_REQUEST,
                    HttpStatus.BAD_REQUEST.value(),
                    Message.HAD_SUBSCRIBED.value(), null);
        return null;
    }

}
