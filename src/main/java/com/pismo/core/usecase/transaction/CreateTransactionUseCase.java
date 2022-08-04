package com.pismo.core.usecase.transaction;

import com.pismo.core.domain.account.Account;
import com.pismo.core.domain.transaction.Transaction;
import com.pismo.core.exceptions.NotFoundException;
import com.pismo.core.exceptions.UnprocessableEntityException;
import com.pismo.core.usecase.account.FindAccountByIdUseCase;
import com.pismo.core.usecase.account.UpdateAvailableLimitUseCase;
import com.pismo.data.entities.TransactionEntity;
import com.pismo.data.repository.AccountRepository;
import com.pismo.data.repository.OperationTypeRepository;
import com.pismo.data.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateTransactionUseCase {

    private final FindAccountByIdUseCase findAccountByIdUseCase;
    private final OperationTypeRepository operationTypeRepository;
    private final TransactionRepository transactionRepository;
    private final UpdateAvailableLimitUseCase updateAvailableLimitUseCase;

    @Transactional(rollbackFor = Exception.class)
    public Transaction doExecute(final Transaction transaction) {
        if (!operationTypeRepository.existsById(transaction.getOperationType().getCode())) {
            throw new UnprocessableEntityException("Not supported this operation type");
        }

        final Account account = this.findAccountByIdUseCase.doExecute(transaction.getAccountId());
        if (transaction.getAmount().compareTo(account.getAvailableLimit()) > 0 && transaction.isNotPayment()) {
            throw new UnprocessableEntityException("Limit exceed");
        }

        final Transaction transactionSaved = this.transactionRepository.save(TransactionEntity.toEntity(transaction))
            .toDomain();

        this.updateAvailableLimitUseCase.doExecute(account, transactionSaved.getAmount());
        return transactionSaved;
    }

}
