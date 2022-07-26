package com.pismo.core.usecase.transaction;

import com.pismo.core.domain.transaction.Transaction;
import com.pismo.core.exceptions.NotFoundException;
import com.pismo.core.exceptions.UnprocessableEntityException;
import com.pismo.data.entities.TransactionEntity;
import com.pismo.data.repository.AccountRepository;
import com.pismo.data.repository.OperationTypeRepository;
import com.pismo.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final AccountRepository accountRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final TransactionRepository transactionRepository;

    public Transaction doExecute(final Transaction transaction) {
        if (!operationTypeRepository.existsById(transaction.getOperationType().getCode())) {
            throw new UnprocessableEntityException("Not supported this operation type");
        }

        if (!accountRepository.existsById(transaction.getAccountId())) {
            throw new NotFoundException("Not found any account with ID: " + transaction.getAccountId());
        }

        return this.transactionRepository.save(TransactionEntity.toEntity(transaction))
            .toDomain();
    }

}
