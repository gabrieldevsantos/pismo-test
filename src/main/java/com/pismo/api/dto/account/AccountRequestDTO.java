package com.pismo.api.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pismo.core.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    @Size(min = 11, max = 14, message = "Document Number should have 11 or 14 characters")
    @NotBlank(message = "Document Number cannot be null")
    @JsonProperty("document_number")
    private String documentNumber;

    public Account toDomain(){
        return new Account(null, documentNumber);
    }

}
