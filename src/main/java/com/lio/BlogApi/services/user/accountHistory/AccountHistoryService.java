package com.lio.BlogApi.services.user.accountHistory;

import com.lio.BlogApi.models.entities.Account;

public interface AccountHistoryService {

    void saveHistory(Account account, String historyContent);

}
