package com.pismo.api.controllers;

import com.pismo.api.dto.account.AccountRequestDTO;
import com.pismo.api.dto.account.AccountResponseDTO;
import com.pismo.core.domain.account.Account;
import com.pismo.core.usecase.account.CreateAccountUseCase;
import com.pismo.core.usecase.account.FindAccountByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pismo.api.dto.account.AccountResponseDTO.toDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final FindAccountByIdUseCase findAccountByIdUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponseDTO create(@RequestBody @Valid AccountRequestDTO accountRequestDTO) {
        return toDto(this.createAccountUseCase.doExecute(accountRequestDTO.toDomain()));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{accountId}")
    public AccountResponseDTO get(@PathVariable Long accountId) {
        final var account = this.findAccountByIdUseCase.doExecute(accountId);
        return toDto(account);
    }
}
