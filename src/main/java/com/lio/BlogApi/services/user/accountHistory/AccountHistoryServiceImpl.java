package com.lio.BlogApi.services.user.accountHistory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.AccountHistory;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.utils.GeneratorUtil;

@Service("accountHistoryService")
public class AccountHistoryServiceImpl implements AccountHistoryService {

    @Override
    public void saveHistory(Account account, String historyContent) {
        AccountHistory accountHistory = AccountHistory.builder()
                .account(account)
                .content(historyContent)
                .createdDate(LocalDateTime.now())
                .viewId(GeneratorUtil.generateId(Prefix.ACC_HISTORY.value(), ViewId.ACC_HISTORY.bound()))
                .build();

    }

}
