package com.lio.BlogApi.controllers.user.account;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.models.dtos.request.RegisterRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.RegisterResponseDTO;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.services.user.account.AccountService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.ResponseUtil;

@RestController
public class AccountController extends BaseController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "${api.account.register}")
    public ResponseEntity<ApiResponse<?>> registerAccount(
            @Valid @RequestBody RegisterRequestDTO registerRequestDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    ResponseUtil.errorResponse(
                            HttpStatus.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST.value(),
                            Message.INVALID_REQUEST_BODY.value(),
                            ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)));
        }

        ApiResponse<?> validationResponse = this.accountService.validateAccountRegistration(registerRequestDTO);

        if (validationResponse != null) {
            return ResponseEntity.badRequest().body(validationResponse);
        }

        RegisterResponseDTO registerResponseDTO = this.accountService.createAccount(registerRequestDTO);

        if (registerResponseDTO == null) {
            return ResponseEntity.badRequest()
                    .body(ResponseUtil.errorResponse(
                            HttpStatus.BAD_REQUEST,
                            HttpStatus.BAD_REQUEST.value(),
                            Message.UNKNOWN_ERROR.value(),
                            ErrorMapUtil.getErrorMapFromValue(Message.UNKNOWN_ERROR.value())));
        }

        return ResponseEntity.ok().body(
                ResponseUtil.response(
                        HttpStatus.OK,
                        HttpStatus.OK.value(),
                        Message.REGISTER_SUCCESS.value(),
                        registerResponseDTO));

    }

}
