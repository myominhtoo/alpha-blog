package com.lio.BlogApi.repositories.accountHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.BlogApi.models.entities.AccountHistory;

@Repository("accountHistoryRepository")
public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Integer> {

}
