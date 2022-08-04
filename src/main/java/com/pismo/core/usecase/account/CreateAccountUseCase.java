package com.pismo.core.usecase.account;

import com.pismo.core.domain.account.Account;
import com.pismo.core.exceptions.UnprocessableEntityException;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountRepository accountRepository;

    public Account doExecute(final Account account) {
        if(this.accountRepository.existsAccountEntitiesByDocumentNumber(account.getDocumentNumber())) {
            throw new UnprocessableEntityException("Already exists account with this document number");
        }

        final var accountSaved = this.accountRepository.save(AccountEntity.of(account.getDocumentNumber(), account.getAvailableLimit()));
        return Account.of(accountSaved.getId(), accountSaved.getDocumentNumber(), accountSaved.getAvailableLimit());
    }

}
