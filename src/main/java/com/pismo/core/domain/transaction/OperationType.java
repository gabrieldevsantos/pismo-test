package com.pismo.core.domain.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.UnaryOperator;

@Getter
@AllArgsConstructor
public enum OperationType {

    BUY_AT_CASH(1, BigDecimal::negate),
    BUY_IN_INSTALLMENTS(2, BigDecimal::negate),
    WITHDRAW(3, BigDecimal::negate),
    PAYMENT(4, input -> input);

    private final Integer code;
    private final UnaryOperator<BigDecimal> amountByOperationType;

    public static OperationType getOperationByCode(final Integer code) {
        return Arrays.stream(OperationType.values())
            .filter(operationType -> code.equals(operationType.getCode()))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
