package com.pismo.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    @JsonProperty("document_number")
    private String documentNumber;

    public Account toDomain(){
        return new Account(null, documentNumber);
    }

}
