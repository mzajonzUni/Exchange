package pl.zajonz.exchange.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;
import pl.zajonz.exchange.service.EmailServiceImpl;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "JanKo", password = "TwojaStara", roles = "ADMIN")
public class ExchangeControllerMockBeanTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ExchangeApiClient exchangeApiClient;
    @MockBean
    private AvailableCurrencies availableCurrencies;
    @MockBean
    private EmailServiceImpl emailService;


    @Test
    void testFindAllCurrencies() throws Exception {
        //given
        Map<String, String> currencies = Map.of("PLN", "Poland", "EUR", "Euro");
        AvailableCurrencies availableCurr = new AvailableCurrencies(true, currencies);
        when(exchangeApiClient.getCurrencies()).thenReturn(availableCurr);

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

        when(exchangeApiClient.exchangeCurrency(any(String.class), any(String.class),
                any(String.class))).thenReturn(currencyExchange);
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

        when(exchangeApiClient.exchangeCurrency(any(String.class), any(String.class),
                any(String.class))).thenReturn(currencyExchange);
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
        verify(emailService).sendExchangeCurrencyMessage(email,currencyExchange);
    }
}
