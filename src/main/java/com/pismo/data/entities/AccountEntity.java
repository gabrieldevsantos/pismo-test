package com.pismo.data.entities;

import com.pismo.core.domain.account.Account;
import com.pismo.core.domain.transaction.Transaction;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity(name = "ACCOUNT")
@Builder
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;

    @Column(name = "document_number", updatable = false, unique = true, nullable = false)
    private String documentNumber;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "account")
    private List<TransactionEntity> transactions;

    public static AccountEntity of(final String documentNumber) {
        return new AccountEntity(documentNumber);
    }
    private AccountEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account toDomain() {
        return new Account(this.id, this.getDocumentNumber(), mapTransactionToDomain(this.transactions));
    }

    private static List<Transaction> mapTransactionToDomain(List<TransactionEntity> transactions) {
        return transactions.stream()
            .map(transactionEntity -> transactionEntity.toDomain(transactionEntity))
            .collect(Collectors.toList());
    }
}
