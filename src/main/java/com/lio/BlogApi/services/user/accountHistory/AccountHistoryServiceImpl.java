package com.lio.BlogApi.services.user.accountHistory;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.AccountHistory;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.accountHistory.AccountHistoryRepository;
import com.lio.BlogApi.utils.GeneratorUtil;

import lombok.AllArgsConstructor;

@Service("accountHistoryService")
@AllArgsConstructor
public class AccountHistoryServiceImpl implements AccountHistoryService {

    private final AccountHistoryRepository accountHistoryRepo;

    /*
     * for saving account activities as history
     */
    @Override
    public void saveHistory(Account account, String historyContent) {
        AccountHistory accountHistory = AccountHistory.builder()
                .account(account)
                .content(historyContent)
                .createdDate(LocalDateTime.now())
                .viewId(GeneratorUtil.generateId(Prefix.ACC_HISTORY.value(), ViewId.ACC_HISTORY.bound()))
                .build();
        this.accountHistoryRepo.save(accountHistory);
    }

}
