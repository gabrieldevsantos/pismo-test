package com.pismo.core.domain.account;

import com.pismo.core.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@Getter
@AllArgsConstructor
public class Account {

    private final Long accountId;
    private final String documentNumber;
    private final List<Transaction> transactions;

    public Account(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.transactions = new ArrayList<>();
    }

    public List<Transaction> getTransactions() {
        if (isNull(transactions)) return emptyList();
        return Collections.unmodifiableList(transactions);
    }
}
