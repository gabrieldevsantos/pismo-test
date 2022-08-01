package com.pismo.data.entities;

import com.pismo.core.domain.account.Account;
import lombok.*;

import javax.persistence.*;

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

    public static AccountEntity of(final String documentNumber) {
        return new AccountEntity(documentNumber);
    }

    private AccountEntity(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Account toDomain() {
        return Account.of(this.id, this.getDocumentNumber());
    }
}
