package pl.zajonz.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;
import pl.zajonz.exchange.service.ExchangeServiceImpl;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ExchangeController.class)
public class ExchangeControllerMockBeanTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ExchangeServiceImpl exchangeService;
    @MockBean
    private AvailableCurrencies availableCurrencies;


    @Test
    void testFindAllCurrencies() throws Exception {
        //given
        Map<String, String> currencies = Map.of("PLN", "Poland", "EUR", "Euro");
        AvailableCurrencies availableCurrencies = new AvailableCurrencies(true, currencies);
        when(exchangeService.getAllCurrencies()).thenReturn(availableCurrencies);

        //when //then
        mockMvc.perform(get("/api/v1/exchange/currencies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.symbols.PLN", equalTo("Poland")));
    }

    @Test
    public void testConvertCurrency() throws Exception {
        //given
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("PLN");
        command.setTo("EUR");
        command.setAmount("100");
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                new CurrencyExchange.Query("PLN", "EUR", "100"), 400,
                new CurrencyExchange.CurrencyExchangeInfo(1, 4.7));

        when(exchangeService.exchangeCurrency(command)).thenReturn(currencyExchange);
        when(availableCurrencies.getSymbols()).thenReturn(Map.of("PLN", "Poland", "EUR", "Europe"));

        //when //then
        mockMvc.perform(get("/api/v1/exchange/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.info.timestamp", notNullValue()))
                .andExpect(jsonPath("$.info.rate", notNullValue()));
    }

    @Test
    public void testConvertCurrencyValidationFailedLackOfBodyParams() throws Exception {
        //given
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();

        when(availableCurrencies.getSymbols()).thenReturn(Map.of("PLN", "Poland", "EUR", "Europe"));

        //when //then
        mockMvc.perform(
                        get("/api/v1/exchange/convert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Validation errors")))
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[*].field",
                        containsInAnyOrder("to", "from", "amount")))
                .andExpect(jsonPath("$.violations[*].message",
                        containsInAnyOrder("must not be blank", "must not be blank", "must not be blank")));
    }

    @Test
    public void testConvertCurrencyValidationFailedCurrencyNotExist() throws Exception {
        //given
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("ASD");
        command.setTo("GHE");
        command.setAmount("100");

        when(availableCurrencies.getSymbols()).thenReturn(Map.of("PLN", "Poland", "EUR", "Europe"));

        //when //then
        mockMvc.perform(
                        get("/api/v1/exchange/convert")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(command))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", equalTo("Validation errors")))
                .andExpect(jsonPath("$.violations").isArray())
                .andExpect(jsonPath("$.violations[*].field", containsInAnyOrder("to", "from")))
                .andExpect(jsonPath("$.violations[*].message",
                        containsInAnyOrder("Currency is not available", "Currency is not available")));
    }

    @Test
    void testExchangeSend() throws Exception {
        //given
        String email = "test@gmail.com";
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("PLN");
        command.setTo("EUR");
        command.setAmount("100");
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                new CurrencyExchange.Query("PLN", "EUR", "100"), 400,
                new CurrencyExchange.CurrencyExchangeInfo(1, 4.7));

        when(exchangeService.exchangeSend(email, command)).thenReturn(currencyExchange);
        when(availableCurrencies.getSymbols()).thenReturn(Map.of("PLN", "Poland", "EUR", "Europe"));

        //when //then
        mockMvc.perform(get("/api/v1/exchange/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", equalTo(true)))
                .andExpect(jsonPath("$.result", notNullValue()))
                .andExpect(jsonPath("$.info.timestamp", notNullValue()))
                .andExpect(jsonPath("$.info.rate", notNullValue()));
    }
}
