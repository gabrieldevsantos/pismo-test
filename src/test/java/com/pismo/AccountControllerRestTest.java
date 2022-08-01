package com.pismo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.api.dto.account.AccountRequestDTO;
import com.pismo.api.dto.account.AccountResponseDTO;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.repository.AccountRepository;
import com.pismo.data.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        this.transactionRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    void shouldCreateAccountWithSuccess() throws Exception {
        final AccountRequestDTO accountRequestDTO = new AccountRequestDTO("12345678910");

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accountRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();
        final AccountResponseDTO accountResponseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), AccountResponseDTO.class);

        final Optional<AccountEntity> accountEntity = this.accountRepository.findById(accountResponseDTO.getAccountId());
        Assertions.assertTrue(accountEntity.isPresent());
        Assertions.assertEquals("12345678910", accountEntity.get().getDocumentNumber());
    }

    @Test
    void shouldThrowException_InvalidDocumentNumber() throws Exception {
        final AccountRequestDTO accountRequestDTO = new AccountRequestDTO("12345");

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accountRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldThrowException_DuplicatedUser() throws Exception {
        final AccountEntity accountEntity = AccountEntity.of("12345678910");
        accountRepository.save(accountEntity);

        final AccountRequestDTO accountRequestDTO = new AccountRequestDTO("12345678910");

        mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(accountRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void shouldRetrieveAccountWithSuccess() throws Exception {
        final AccountEntity accountEntity = AccountEntity.of("12345678910");
        final AccountEntity accountSaved = accountRepository.save(accountEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/" + accountSaved.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldThrowException_NotFoundAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accounts/123")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
