package com.lio.BlogApi.services.user.account;

import com.lio.BlogApi.models.dtos.request.RegisterRequestDTO;
import com.lio.BlogApi.models.dtos.response.ApiResponse;
import com.lio.BlogApi.models.dtos.response.RegisterResponseDTO;

public interface AccountService {

    boolean isDuplicateEmail(String email);

    boolean isAccountLocked(String email);

    boolean isAccountEnabled(String email);

    ApiResponse<?> validateAccountRegistration(RegisterRequestDTO registerRequestDTO);

    RegisterResponseDTO createAccount(RegisterRequestDTO registerRequestDTO);

}
