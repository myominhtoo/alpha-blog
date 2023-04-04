package com.lio.BlogApi.services.user.accountCode;

import com.lio.BlogApi.models.entities.Account;

public interface AccountCodeService {

    String generateVerificationCode(Account account);

    boolean isExpiredCode(String code, String accountId);

    void resetAllPreviousCodes(Integer accountId);

}
