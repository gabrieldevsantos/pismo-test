package com.pismo.core.domain.account;

import lombok.Getter;

@Getter
public class Account {

    private final Long accountId;
    private final String documentNumber;

    public static Account of(final Long accountId, final String documentNumber) {
        return new Account(accountId, documentNumber);
    }

    private Account(Long accountId, String documentNumber) {
        this.accountId = accountId;
        this.documentNumber = documentNumber;
    }

}
