package com.pismo.core.usecase.transaction;

import com.pismo.core.domain.account.Account;
import com.pismo.core.domain.transaction.OperationType;
import com.pismo.core.domain.transaction.Transaction;
import com.pismo.core.exceptions.NotFoundException;
import com.pismo.core.exceptions.UnprocessableEntityException;
import com.pismo.core.usecase.account.FindAccountByIdUseCase;
import com.pismo.core.usecase.account.UpdateAvailableLimitUseCase;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.entities.OperationTypeEntity;
import com.pismo.data.entities.TransactionEntity;
import com.pismo.data.repository.AccountRepository;
import com.pismo.data.repository.OperationTypeRepository;
import com.pismo.data.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class CreateTransactionUseCaseTest {

    @Mock
    private FindAccountByIdUseCase findAccountByIdUseCase;

    @Mock
    private OperationTypeRepository operationTypeRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UpdateAvailableLimitUseCase updateAvailableLimitUseCase;

    private CreateTransactionUseCase createTransactionUseCase;

    @BeforeEach
    void setUp() {
        createTransactionUseCase = new CreateTransactionUseCase(findAccountByIdUseCase, operationTypeRepository, transactionRepository, updateAvailableLimitUseCase);
    }

    @Test
    void mustCreateTransactionWithSuccess() {
        final Account account = Account.of(null, "19091312025", BigDecimal.TEN);
        Mockito.when(operationTypeRepository.existsById(Mockito.anyInt())).thenReturn(Boolean.TRUE);
        Mockito.when(findAccountByIdUseCase.doExecute(Mockito.anyLong())).thenReturn(account);
        Mockito.when(transactionRepository.save(Mockito.any())).thenReturn(mockTransactionEntity());

        final Transaction transaction = new Transaction(null, 123456L, OperationType.BUY_AT_CASH, new BigDecimal(10));
        final Transaction transactionSaved = createTransactionUseCase.doExecute(transaction);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(new BigDecimal(-10.00), transactionSaved.getAmount());
            Assertions.assertEquals(1L, transactionSaved.getId());
            Assertions.assertEquals(1L, transactionSaved.getAccountId());
        });

        Mockito.verify(transactionRepository).save(Mockito.any());
    }

    @Test
    void throwException_invalidOperationType() {
        Mockito.when(operationTypeRepository.existsById(Mockito.anyInt())).thenReturn(Boolean.FALSE);

        final Transaction transaction = new Transaction(null, 123456L, OperationType.BUY_AT_CASH, new BigDecimal(10));
        Assertions.assertThrows(UnprocessableEntityException.class, () -> createTransactionUseCase.doExecute(transaction));

        Mockito.verify(transactionRepository, VerificationModeFactory.noInteractions()).save(Mockito.any());
    }

    @Test
    void throwException_notFoundAccount() {
        Mockito.when(findAccountByIdUseCase.doExecute(Mockito.anyLong())).thenThrow(NotFoundException.class);
        Mockito.when(operationTypeRepository.existsById(Mockito.anyInt())).thenReturn(Boolean.TRUE);

        final Transaction transaction = new Transaction(null, 123456L, OperationType.BUY_AT_CASH, new BigDecimal(10));
        Assertions.assertThrows(NotFoundException.class, () -> createTransactionUseCase.doExecute(transaction));

        Mockito.verify(transactionRepository, VerificationModeFactory.noInteractions()).save(Mockito.any());
    }

    public TransactionEntity mockTransactionEntity() {
        return TransactionEntity.builder()
            .id(1L)
            .account(AccountEntity.builder().id(1L).build())
            .operationType(new OperationTypeEntity(1))
            .amount(new BigDecimal(-10))
            .build();
    }
}
