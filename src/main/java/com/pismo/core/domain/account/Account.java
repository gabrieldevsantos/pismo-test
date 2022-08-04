package com.pismo.core.domain.account;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Account {

    private final Long accountId;
    private final String documentNumber;
    private final BigDecimal availableLimit;

    public static Account of(final Long accountId, final String documentNumber, final BigDecimal availableLimit) {
        return new Account(accountId, documentNumber, availableLimit);
    }

    private Account(Long accountId, String documentNumber, BigDecimal availableLimit) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
        this.availableLimit = availableLimit;
    }

}
