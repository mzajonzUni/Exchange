package pl.zajonz.exchange.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.model.AvailableCurrencies;

import java.util.Map;

import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;

@ContextConfiguration(classes = {ExchangeServiceImpl.class})
@ExtendWith(MockitoExtension.class)
public class ExchangeServiceImplTest {

    @InjectMocks
    private ExchangeServiceImpl exchangeService;
    @Mock
    private AvailableCurrencies availableCurrencies;
    @Mock
    private ExchangeApiClient exchangeApiClient;

    @Test
    void getAllCurrencies() {
//        // given
//        AvailableCurrencies allCurrencies = new AvailableCurrencies(true, Map.of("USD", "United States Dollar"));
//
//        // when
//        AvailableCurrencies currencies = exchangeService.getAllCurrencies();
//
//        // then
//        assertEquals(allCurrencies.getSymbols(), currencies.getSymbols());

//        AvailableCurrencies availableCurrencies = new AvailableCurrencies(true, Map.of("USD", "United States Dollar"));
//
//        assertSame(availableCurrencies, (new ExchangeServiceImpl(availableCurrencies, null)).getAllCurrencies());
    }
    @Test
    void testExchangeCurrency() {
        // given
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(), 200.00,
                new CurrencyExchange.CurrencyExchangeInfo(10L, 2.00));

        when(exchangeApiClient.exchangeCurrency(any(), any(), any())).thenReturn(currencyExchange);

        CurrencyExchangeCommand currencyExchangeCommand = new CurrencyExchangeCommand();
        currencyExchangeCommand.setAmount("100");
        currencyExchangeCommand.setFrom("PLN");
        currencyExchangeCommand.setTo("USD");

        // when
        CurrencyExchange currencyExchangeResult = exchangeService.exchangeCurrency(currencyExchangeCommand);

        // then
        assertSame(currencyExchange, currencyExchangeResult);
        verify(exchangeApiClient).exchangeCurrency(any(),any(),any());
    }
}
