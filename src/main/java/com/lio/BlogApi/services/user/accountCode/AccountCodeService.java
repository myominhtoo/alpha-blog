package com.lio.BlogApi.services.user.accountCode;

import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.AccountCode;

public interface AccountCodeService {

    String generateVerificationCode(Account account);

    boolean isExpiredCode(AccountCode accountCode);

    void resetAllPreviousCodes(Integer accountId);

    boolean verifyAccountCode(String accountViewId, String code);

}
