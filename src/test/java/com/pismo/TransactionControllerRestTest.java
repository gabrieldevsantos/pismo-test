package com.pismo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pismo.api.dto.transaction.TransactionRequestDTO;
import com.pismo.api.dto.transaction.TransactionResponseDTO;
import com.pismo.data.entities.AccountEntity;
import com.pismo.data.entities.TransactionEntity;
import com.pismo.data.repository.AccountRepository;
import com.pismo.data.repository.OperationTypeRepository;
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

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private OperationTypeRepository operationTypeRepository;

    @BeforeEach
    void setUp() {
        this.transactionRepository.deleteAll();
        this.accountRepository.deleteAll();
    }

    @Test
    void shouldCreateNewTransaction_buyOperation() throws Exception {
        //create user
        final AccountEntity accountSaved = this.accountRepository.save(AccountEntity.of("12345678910"));

        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountSaved.getId(), 1, new BigDecimal("10"));

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();
        final TransactionResponseDTO transactionResponseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseDTO.class);

        final Optional<TransactionEntity> transactionEntity = this.transactionRepository.findById(transactionResponseDTO.getId());
        Assertions.assertTrue(transactionEntity.isPresent());
        Assertions.assertEquals(1, transactionEntity.get().getOperationType().getId());
        Assertions.assertEquals(new BigDecimal("-10.00"), transactionEntity.get().getAmount());
        Assertions.assertNotNull(transactionEntity.get().getEventDate());
    }

    @Test
    void shouldCreateNewTransaction_paymentOperation() throws Exception {
        //create user
        final AccountEntity accountSaved = this.accountRepository.save(AccountEntity.of("12345678910"));

        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountSaved.getId(), 4, new BigDecimal("25"));

        final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();
        final TransactionResponseDTO transactionResponseDTO = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), TransactionResponseDTO.class);

        final Optional<TransactionEntity> transactionEntity = this.transactionRepository.findById(transactionResponseDTO.getId());
        Assertions.assertTrue(transactionEntity.isPresent());
        Assertions.assertEquals(4, transactionEntity.get().getOperationType().getId());
        Assertions.assertEquals(new BigDecimal("25.00"), transactionEntity.get().getAmount());
        Assertions.assertNotNull(transactionEntity.get().getEventDate());
    }

    @Test
    void shouldThrowException_userNotFound() throws Exception {
        //not create user

        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1L, 4, new BigDecimal("25"));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldThrowException_negativeAmount() throws Exception {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1L, 1, new BigDecimal(-10));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldThrowException_invalidOperationTypeId() throws Exception {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1L, 2, new BigDecimal(-10));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldThrowException_invalidAccountNumber() throws Exception {
        final TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, 1, new BigDecimal(10));

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(transactionRequestDTO)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
