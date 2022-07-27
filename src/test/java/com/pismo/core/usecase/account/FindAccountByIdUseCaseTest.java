package com.pismo.core.usecase.account;

import com.pismo.core.domain.account.Account;
import com.pismo.core.exceptions.NotFoundException;
import com.pismo.core.exceptions.UnprocessableEntityException;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class FindAccountByIdUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    private FindAccountByIdUseCase findAccountByIdUseCase;

    @BeforeEach
    void setUp() {
        findAccountByIdUseCase = new FindAccountByIdUseCase(accountRepository);
    }

    @Test
    void mustRetrieveAccountById() {
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockAccountEntity()));

        final Account accountSaved = findAccountByIdUseCase.doExecute(1L);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(1L, accountSaved.getAccountId());
            Assertions.assertEquals("19091312025", accountSaved.getDocumentNumber());
        });

        Mockito.verify(accountRepository).findById(Mockito.anyLong());
    }

    @Test
    void throwException_duplicateAccountNumber() {
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> findAccountByIdUseCase.doExecute(1L));
    }

    public AccountEntity mockAccountEntity() {
        return AccountEntity.builder()
            .id(1L)
            .documentNumber("19091312025")
            .transactions(List.of())
            .build();
    }

}
