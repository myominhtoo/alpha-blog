package com.lio.BlogApi.services.user.accountCode;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.entities.AccountCode;
import com.lio.BlogApi.models.enums.CodeStatus;
import com.lio.BlogApi.models.enums.History;
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.accountCode.AccountCodeRepository;
import com.lio.BlogApi.services.user.accountHistory.AccountHistoryService;
import com.lio.BlogApi.utils.GeneratorUtil;

@Service("accountCodeService")
public class AccountCodeServiceImpl implements AccountCodeService {

    private final AccountCodeRepository accountCodeRepo;
    private final AccountHistoryService accountHistoryService;

    public AccountCodeServiceImpl(
            AccountCodeRepository accountCodeRepo,
            AccountHistoryService accountHistoryService) {
        this.accountCodeRepo = accountCodeRepo;
        this.accountHistoryService = accountHistoryService;
    }

    @Override
    public String generateVerificationCode(Account account) {

        /*
         * reset previous codes to disable
         */
        this.resetAllPreviousCodes(account.getId());

        Calendar calendar = Calendar.getInstance();
        /*
         * added current time to 10 minutes
         */
        calendar.add(Calendar.MINUTE, 10);

        AccountCode accountCode = AccountCode.builder()
                .account(account)
                .code(GeneratorUtil.generateId(Prefix.CODE.value(), ViewId.CODE.bound()))
                .isActive(true)
                .codeStatus(CodeStatus.NOT_VERIFIED.status())
                .createdDate(LocalDateTime.now())
                .updatedDate(null)
                .expiredDate(calendar.getTime())
                .build();

        accountCode = this.accountCodeRepo.save(accountCode);
        return accountCode == null ? null : accountCode.getCode();
    }

    @Override
    public boolean isExpiredCode(AccountCode accountCode) {
        return accountCode.getExpiredDate().before(new Date());
    }

    @Override
    @Transactional
    public void resetAllPreviousCodes(Integer accountId) {
        List<AccountCode> previousAccountCodes = this.accountCodeRepo.findByAccountId(accountId);
        previousAccountCodes.stream()
                .forEach(accountCode -> {
                    accountCode.setActive(false);
                    accountCode.setUpdatedDate(LocalDateTime.now());
                    accountCode.setCodeStatus(CodeStatus.RESET.status());
                    this.accountCodeRepo.save(accountCode);
                });

    }

    @Override
    @Transactional
    public boolean verifyAccountCode(String accountViewId, String code) {
        /*
         * getting active code for account
         */
        Optional<AccountCode> accountCode$ = this.accountCodeRepo
                .findByCodeAndAccountViewIdAndIsActiveTrue(code, accountViewId);

        /*
         * verification will be false if empty and expire code
         */
        if (accountCode$.isEmpty() || this.isExpiredCode(accountCode$.get()))
            return false;

        /*
         * will confirm if code will be equal
         */
        if (accountCode$.get().getCode().equals(code)) {
            AccountCode accountCode = accountCode$.get();
            accountCode.setActive(false);
            accountCode.setUpdatedDate(LocalDateTime.now());

            this.accountCodeRepo.save(accountCode);

            this.accountHistoryService
                    .saveHistory(accountCode$.get().getAccount(), History.ACCOUNT_VERIFIED.value());
            return true;
        }

        return false;
    }

}
