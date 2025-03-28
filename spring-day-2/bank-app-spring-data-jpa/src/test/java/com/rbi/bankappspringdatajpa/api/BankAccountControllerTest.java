package com.rbi.bankappspringdatajpa.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.rbi.bankappspringdatajpa.model.BankAccount;
import com.rbi.bankappspringdatajpa.service.BankAccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BankAccountController.class)
@ExtendWith(SpringExtension.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankAccountServiceImpl service;

    @Test
    void findAccount() throws Exception {

        BankAccount account = new BankAccount("123456789", "John", 2000, "Savings", null);

        Mockito.when(service.getAccountDetails(account.getAccountNumber())).thenReturn(account);

        mockMvc.perform(get("/api/v1/accounts/" + account.getAccountNumber()))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).getAccountDetails(account.getAccountNumber());

    }

    @Test
    void createAccountTest() throws Exception {

        BankAccount account = new BankAccount("123456789", "John", 2000, "Savings", null);

        Mockito.when(service.createAccount(account)).thenReturn(account);

        mockMvc.perform(post("/api/v1/accounts").
                        content(mapToJson(account))
                        .contentType("application/json")
                )
                .andExpect(status().isCreated())
                //.andExpect(content().contentType("application/json"))
                .andExpect(content().json("""
                                                {
                                                  "accountNumber": "123456789",
                                                  "accountHolderName": "John",
                                                  "balance": 2000,
                                                  "accountType": "Savings"
                                                }
                                """))
                        .andReturn();

    }


    public String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
        return mapper.writeValueAsString(object);
    }


}