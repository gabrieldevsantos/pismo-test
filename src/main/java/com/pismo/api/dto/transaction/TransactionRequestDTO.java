package com.pismo.api.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.transaction.OperationType;
import com.pismo.core.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("operation_type_id")
    private Integer operationTypeId;

    @JsonProperty("amount")
    private BigDecimal amount;

    public Transaction toDomain() {
        return new Transaction(null,
            accountId,
            OperationType.getOperationByCode(operationTypeId),
            amount);
    }

}
