package com.pismo.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("document_number")
    private String documentNumber;

    public static AccountResponseDTO toDto(final Account account) {
        return new AccountResponseDTO(account.getAccountId(), account.getDocumentNumber());
    }

}
