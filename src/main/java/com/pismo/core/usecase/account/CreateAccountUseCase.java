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

        final AccountEntity accountEntity = new AccountEntity(account.getDocumentNumber());
        final AccountEntity savedAccount = this.accountRepository.save(accountEntity);
        return new Account(savedAccount.getId(), savedAccount.getDocumentNumber());
    }

}
