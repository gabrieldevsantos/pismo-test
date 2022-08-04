package com.pismo.core.usecase.account;

import com.pismo.core.domain.account.Account;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class UpdateAvailableLimitUseCaseTest {

    private UpdateAvailableLimitUseCase updateAvailableLimitUseCase;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        updateAvailableLimitUseCase = new UpdateAvailableLimitUseCase(accountRepository);
    }

    @Captor
    private ArgumentCaptor<AccountEntity> accountEntityArgumentCaptor;

    @Test
    void mustUpdateAvailableLimit_withSuccess() {
        Account account = Account.of(1L, "328748723984", new BigDecimal(-30));
        updateAvailableLimitUseCase.doExecute(account, new BigDecimal(10));
        Mockito.verify(accountRepository).save(accountEntityArgumentCaptor.capture());

        Assertions.assertEquals("328748723984", accountEntityArgumentCaptor.getValue().getDocumentNumber());
        Assertions.assertEquals(1L, accountEntityArgumentCaptor.getValue().getId());
        Assertions.assertEquals(new BigDecimal(-20), accountEntityArgumentCaptor.getValue().getAvailableLimit());
    }
}
