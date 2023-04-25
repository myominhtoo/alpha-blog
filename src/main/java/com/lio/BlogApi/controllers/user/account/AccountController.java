package com.lio.BlogApi.controllers.user.account;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lio.BlogApi.controllers.BaseController;
import com.lio.BlogApi.models.dtos.custom.AppUserDetails;
import com.lio.BlogApi.models.dtos.request.user.LoginRequestDTO;
import com.lio.BlogApi.models.dtos.request.user.RegisterRequestDTO;
import com.lio.BlogApi.models.dtos.response.general.ApiResponse;
import com.lio.BlogApi.models.dtos.response.user.RegisterResponseDTO;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.models.enums.SecretWord;
import com.lio.BlogApi.services.common.jwtToken.JwtTokenService;
import com.lio.BlogApi.services.user.account.AccountService;
import com.lio.BlogApi.utils.ErrorMapUtil;
import com.lio.BlogApi.utils.HeaderUtil;
import com.lio.BlogApi.utils.ResponseUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class AccountController extends BaseController {

        private final AccountService accountService;
        private final JwtTokenService jwtTokenService;

        /*
         * for account registration
         * /api/{version}/register
         */
        @PostMapping(value = "${api.account.register}")
        public ResponseEntity<ApiResponse<?>> registerAccount(
                        @Valid @RequestBody RegisterRequestDTO registerRequestDTO,
                        BindingResult bindingResult) throws IOException, MessagingException {

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
                                                        ErrorMapUtil.getErrorMapFromValue(
                                                                        Message.UNKNOWN_ERROR.value())));
                }

                return ResponseEntity.ok().body(
                                ResponseUtil.response(
                                                HttpStatus.OK,
                                                HttpStatus.OK.value(),
                                                Message.REGISTER_SUCCESS.value(),
                                                registerResponseDTO));

        }

        /*
         * for confirming verification
         * /api/{version}/verify-account
         */
        @GetMapping(value = "${api.account.verifyAccount}")
        public ResponseEntity<ApiResponse<?>> verifyRegisteredAccount(
                        HttpServletRequest request) {
                final String verificationCode = request.getParameter(SecretWord.CODE.code());
                final String email = request.getParameter(SecretWord.EMAIL.code());

                ApiResponse<?> validationResponse = this.accountService
                                .validateRegisteredVerification(email, verificationCode);

                if (validationResponse != null) {
                        return ResponseEntity.badRequest().body(validationResponse);
                }

                ApiResponse<?> verifyResponse = this.accountService.verifyRegisteredAccount(email, verificationCode);

                return new ResponseEntity<ApiResponse<?>>(verifyResponse, verifyResponse.getStatus());
        }

        /*
         * for account login
         * will get token as Response Header
         */
        @PostMapping(value = "${api.account.login}")
        public ResponseEntity<ApiResponse<?>> loginAccount(
                        @Valid @RequestBody LoginRequestDTO loginRequestDTO,
                        BindingResult bindingResult) {

                if (bindingResult.hasErrors()) {
                        return ResponseEntity.badRequest().body(
                                        ResponseUtil.errorResponse(
                                                        HttpStatus.BAD_REQUEST,
                                                        HttpStatus.BAD_REQUEST.value(),
                                                        Message.INVALID_REQUEST_BODY.value(),
                                                        ErrorMapUtil.getErrorMapFromBindingResult(bindingResult)));
                }

                Authentication auth = this.accountService.loginAccount(loginRequestDTO);
                AppUserDetails appUser = (AppUserDetails) auth.getPrincipal();
                appUser.setToken(this.jwtTokenService.generateToken(HeaderUtil.getMapFromPrincipal(appUser)));

                HttpHeaders headers = HeaderUtil.getHeadersFromMap(HeaderUtil.getAuthenticationHeader(appUser));

                return new ResponseEntity<ApiResponse<?>>(
                                ResponseUtil.response(
                                                HttpStatus.OK,
                                                HttpStatus.OK.value(),
                                                Message.LOGIN_SUCCESS.value(),
                                                null),
                                headers, HttpStatus.OK);
        }

}
