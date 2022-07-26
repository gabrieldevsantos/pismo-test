package com.pismo.core.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperationType {

    BUY_AT_CASH(1),
    BUY_IN_INSTALLMENTS(2),
    WITHDRAW(3),
    PAYMENT(4);

    private final Integer code;

    public static OperationType getOperationByCode(final Integer code) {
        return Arrays.stream(OperationType.values())
            .filter(operationType -> code.equals(operationType.getCode()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
