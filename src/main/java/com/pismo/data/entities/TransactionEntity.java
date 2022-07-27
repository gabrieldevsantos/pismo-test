package com.pismo.data.entities;

import com.pismo.core.domain.transaction.OperationType;
import com.pismo.core.domain.transaction.Transaction;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity(name = "TRANSACTION")
@Builder
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account", updatable = false, nullable = false)
    private AccountEntity account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private OperationTypeEntity operationType;

    @Column(name = "amount", updatable = false, nullable = false)
    private BigDecimal amount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime eventDate;

    public static TransactionEntity toEntity(final Transaction transaction) {
        return TransactionEntity.builder()
            .account(AccountEntity.builder().id(transaction.getAccountId()).build())
            .operationType(new OperationTypeEntity(transaction.getOperationType().getCode()))
            .amount(transaction.getOperationType().getAmountByOperationType().apply(transaction.getAmount()))
            .build();
    }

    public Transaction toDomain(TransactionEntity transactionEntity) {
        return new Transaction(transactionEntity.getId(),
            transactionEntity.getAccount().getId(),
            OperationType.getOperationByCode(transactionEntity.getOperationType().getId()),
            transactionEntity.getAmount());
    }

    public Transaction toDomain() {
        return new Transaction(this.getId(),
            this.getAccount().getId(),
            OperationType.getOperationByCode(this.getOperationType().getId()),
            this.getAmount());
    }
}
