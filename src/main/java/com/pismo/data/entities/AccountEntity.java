package com.pismo.data.entities;

import com.pismo.core.domain.account.Account;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
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

    @Column(name = "available_limit")
    private BigDecimal availableLimit;

    public static AccountEntity of(final Long id, final String documentNumber, final BigDecimal availableLimit) {
        return new AccountEntity(id, documentNumber, availableLimit);
    }

    public static AccountEntity of(final String documentNumber, final BigDecimal availableLimit) {
        return new AccountEntity(null, documentNumber, availableLimit);
    }

    private AccountEntity(Long id, String documentNumber, BigDecimal availableLimit) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.availableLimit = availableLimit;
    }

    public Account toDomain() {
        return Account.of(this.id, this.getDocumentNumber(), this.availableLimit);
    }
}
