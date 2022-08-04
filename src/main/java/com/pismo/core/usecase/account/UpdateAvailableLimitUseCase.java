package com.pismo.core.usecase.account;

import com.pismo.core.domain.account.Account;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UpdateAvailableLimitUseCase {

    private final AccountRepository accountRepository;

    public void doExecute(final Account account, final BigDecimal transactionAmount) {
        final BigDecimal newLimit = account.getAvailableLimit().add(transactionAmount);
        final AccountEntity accountUpdated = AccountEntity.of(account.getAccountId(), account.getDocumentNumber(), newLimit);
        this.accountRepository.save(accountUpdated);
    }

}
