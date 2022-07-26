package com.pismo.core.usecase.account;

import com.pismo.core.domain.account.Account;
import com.pismo.core.exceptions.NotFoundException;
import com.pismo.data.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FindAccountByIdUseCase {

    private final AccountRepository accountRepository;

    public Account doExecute(final Long id) {
        return this.accountRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found any account with ID: " + id))
            .toDomain();
    }

}
