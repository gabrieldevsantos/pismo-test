package com.pismo.core.domain.transaction;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Transaction {

    private final Long id;
    private final Long accountId;
    private final OperationType operationType;
    private final BigDecimal amount;

    public Transaction(Long id, Long accountId, OperationType operationType, BigDecimal amount) {
        this.id = id;
        this.accountId = accountId;
        this.operationType = operationType;
        this.amount = amount;
    }
}
