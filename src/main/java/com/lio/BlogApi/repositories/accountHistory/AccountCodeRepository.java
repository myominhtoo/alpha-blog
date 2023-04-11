package com.lio.BlogApi.repositories.accountHistory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.AccountCode;

@Repository("accountCodeRepository")
public interface AccountCodeRepository extends JpaRepository<AccountCode, Integer> {

    List<AccountCode> findByAccountId(Integer accountId);

    Optional<AccountCode> findByCodeAndAccountViewIdAndIsActiveTrue(String code, String accountViewId);

}
