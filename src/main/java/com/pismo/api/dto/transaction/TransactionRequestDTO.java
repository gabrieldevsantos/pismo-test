package com.pismo.api.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.transaction.OperationType;
import com.pismo.core.domain.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {

    @Min(1)
    @NotNull(message = "Account id cannot be null")
    @JsonProperty("account_id")
    private Long accountId;

    @Max(value = 5, message = "Operation type id invalid")
    @Min(value = 1, message = "Operation type id invalid")
    @NotNull(message = "Operation type id cannot be null")
    @JsonProperty("operation_type_id")
    private Integer operationTypeId;

    @Positive(message = "Amount cannot be negative")
    @NotNull(message = "Amount cannot be null")
    @JsonProperty("amount")
    private BigDecimal amount;

    public Transaction toDomain() {
        return new Transaction(null,
            accountId,
            OperationType.getOperationByCode(operationTypeId),
            amount);
    }

}
