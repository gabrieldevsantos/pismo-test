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
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseTest {

    @Mock
    private AccountRepository accountRepository;

    private CreateAccountUseCase createAccountUseCase;

    @BeforeEach
    void setUp() {
        createAccountUseCase = new CreateAccountUseCase(accountRepository);
    }

    @Test
    void mustCreateAccountWithSuccess() {
        Mockito.when(accountRepository.existsAccountEntitiesByDocumentNumber(Mockito.anyString())).thenReturn(Boolean.FALSE);
        Mockito.when(accountRepository.save(Mockito.any())).thenReturn(mockAccountEntity());

        final Account account = Account.of(null, "19091312025");
        final Account accountSaved = createAccountUseCase.doExecute(account);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(1L, accountSaved.getAccountId());
            Assertions.assertEquals("19091312025", accountSaved.getDocumentNumber());
        });

        Mockito.verify(accountRepository).save(Mockito.any());
    }

    @Test
    void throwException_duplicateAccountNumber() {
        Mockito.when(accountRepository.existsAccountEntitiesByDocumentNumber(Mockito.anyString())).thenReturn(Boolean.TRUE);

        final Account account = Account.of(null, "19091312025");
        Assertions.assertThrows(UnprocessableEntityException.class, () -> createAccountUseCase.doExecute(account));
    }

    public AccountEntity mockAccountEntity() {
        return AccountEntity.builder()
            .id(1L)
            .documentNumber("19091312025")
            .build();
    }
}
