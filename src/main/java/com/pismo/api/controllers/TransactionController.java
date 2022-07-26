package com.pismo.api.controllers;

import com.pismo.api.dto.transaction.TransactionRequestDTO;
import com.pismo.api.dto.transaction.TransactionResponseDTO;
import com.pismo.core.domain.transaction.Transaction;
import com.pismo.core.usecase.transaction.CreateTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pismo.api.dto.transaction.TransactionResponseDTO.toDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final CreateTransactionUseCase createTransactionUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TransactionResponseDTO create(@RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        final Transaction transactionSaved = this.createTransactionUseCase.doExecute(transactionRequestDTO.toDomain());
        return toDto(transactionSaved);
    }

}
