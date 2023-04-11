package com.lio.BlogApi.services.user.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lio.BlogApi.models.dtos.custom.AppUserDetails;
import com.lio.BlogApi.models.entities.Account;
import com.lio.BlogApi.models.enums.Message;
import com.lio.BlogApi.repositories.account.AccountRepository;

import lombok.AllArgsConstructor;

@Service("customUserDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = this.accountRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(Message.INVALID_ACCOUNT.value()));
        if (account.isDelete() || !account.isActive() || account.isLocked())
            throw new UsernameNotFoundException(Message.INVALID_ACCOUNT.value());
        return new AppUserDetails(account);
    }
}
