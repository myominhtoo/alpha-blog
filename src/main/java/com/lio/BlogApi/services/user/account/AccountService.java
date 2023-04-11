package com.lio.BlogApi.services.user.account;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.security.core.Authentication;

import com.lio.BlogApi.models.dtos.request.LoginRequestDTO;
import com.lio.BlogApi.models.dtos.request.RegisterRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.RegisterResponseDTO;

public interface AccountService {

    boolean isDuplicateEmail(String email);

    boolean isAccountLocked(String email);

    boolean isAccountEnabled(String email);

    ApiResponse<?> validateAccountRegistration(RegisterRequestDTO registerRequestDTO);

    RegisterResponseDTO createAccount(RegisterRequestDTO registerRequestDTO) throws IOException, MessagingException;

    ApiResponse<?> validateRegisteredVerification(String email, String verificationCode);

    ApiResponse<?> verifyRegisteredAccount(String email, String verificationCode);

    Authentication loginAccount(LoginRequestDTO loginRequestDTO);
}
