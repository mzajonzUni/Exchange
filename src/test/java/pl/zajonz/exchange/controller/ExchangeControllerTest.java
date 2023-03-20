package pl.zajonz.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAllCurrencies() throws Exception
    {
        mockMvc.perform(get("/api/v1/exchange/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.symbols.AED", equalTo("United Arab Emirates Dirham")))
        ;
    }

    @Test
    void testConvertCurrency() throws Exception
    {
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("PLN");
        command.setTo("USD");
        command.setAmount("100");

        mockMvc.perform(
                    post("/api/v1/exchange/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.info.timestamp", notNullValue()))
                .andExpect(jsonPath("$.info.rate", notNullValue()))
        ;
    }

    @Test
    void testConvertCurrencyValidationFailedLackOfBodyParams() throws Exception
    {
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();

        mockMvc.perform(
                        post("/api/v1/exchange/convert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Validation errors")))
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[*].field", containsInAnyOrder("to", "from", "amount")))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder("must not be blank", "must not be blank", "must not be blank")))
        ;
    }

    @Test
    void testConvertCurrencyValidationFailedCurrencyNotExist() throws Exception
    {
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("ASD");
        command.setTo("GHE");
        command.setAmount("100");

        mockMvc.perform(
                        post("/api/v1/exchange/convert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Validation errors")))
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[*].field", containsInAnyOrder("to", "from")))
                .andExpect(jsonPath("$.violations[*].message", containsInAnyOrder("Currency is not available", "Currency is not available")))
        ;
    }
}
