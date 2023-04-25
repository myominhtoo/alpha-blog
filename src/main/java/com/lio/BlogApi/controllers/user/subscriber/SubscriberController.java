package com.lio.BlogApi.controllers.user.subscriber;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lio.BlogApi.models.dtos.request.subscriber.SubscriberRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.entities.Subscriber;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.user.subscriber.SubscriberService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;

    /*
     * /api/v1/subscribers
     * requested user with email will be sent email
     * when something changes happened or updated features came
     */
    @PostMapping(value = "${api.subscribers}")
    public ResponseEntity<ApiResponse<?>> subscribeApplication(
            @Valid @RequestBody SubscriberRequestDTO subscriberRequestDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST_BODY.value(),
                                    ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)));
        }

        ApiResponse<?> validationResponse = this.subscriberService.validateSubscriber(subscriberRequestDTO);

        if (validationResponse != null) {
            return ResponseEntity.badRequest().body(validationResponse);
        }

        Subscriber subscriber = this.subscriberService.subscribe(subscriberRequestDTO);

        /*
         * not essential checking condition
         */
        if (subscriber == null) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.UNKNOWN_ERROR.value(),
                                    ErrorMapUtil.getErrorMapFromValue(Message.UNKNOWN_ERROR.value())));
        }

        return ResponseEntity.ok().body(
                ResponseUtil.response(
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Message.SUBSCRIBE_SUCCESS.value(), subscriber));
    }

    /*
     * for cancelling subscriptions back
     */
    @GetMapping(value = "${api.subscribers.unsubscribe}")
    public ResponseEntity<ApiResponse<?>> unsubscribeApplication(
            @PathVariable(value = "subscriberId", required = true) String subscriberId) {
        boolean unsubscribeStatus = this.subscriberService.unsubscribe(subscriberId);

        if (!unsubscribeStatus) {
            return ResponseEntity.badRequest()
                    .body(
                            ResponseUtil.errorResponse(
                                    HttpStatus.BAD_REQUEST,
                                    HttpStatus.BAD_REQUEST.value(),
                                    Message.INVALID_REQUEST.value(),
                                    null));
        }

        return ResponseEntity.ok()
                .body(
                        ResponseUtil.response(
                                HttpStatus.OK,
                                HttpStatus.OK.value(),
                                Message.UNSUBSCRIBE_SUCCESS.value(), null));
    }

}
