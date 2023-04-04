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
import com.lio.BlogApi.models.enums.Prefix;
import com.lio.BlogApi.models.enums.ViewId;
import com.lio.BlogApi.repositories.account.AccountCodeRepository;
import com.lio.BlogApi.utils.GeneratorUtil;

@Service("accountCodeService")
public class AccountCodeServiceImpl implements AccountCodeService {

    private final AccountCodeRepository accountCodeRepo;

    public AccountCodeServiceImpl(
            AccountCodeRepository accountCodeRepo) {
        this.accountCodeRepo = accountCodeRepo;
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
    public boolean isExpiredCode(String code, String accountViewId) {
        Optional<AccountCode> accoundCode$ = this.accountCodeRepo.findByCodeAndAccountViewId(code, accountViewId);
        return accoundCode$.isPresent() ? accoundCode$.get().getExpiredDate().before(new Date()) : true;
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

}
