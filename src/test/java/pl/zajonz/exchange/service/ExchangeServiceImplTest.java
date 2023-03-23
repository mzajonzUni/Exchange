package pl.zajonz.exchange.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceImplTest {

    @InjectMocks
    private ExchangeServiceImpl exchangeService;
    @Mock
    private ExchangeApiClient exchangeApiClient;
    @Mock
    private EmailServiceImpl emailService;

    @Test
    void testGetAllCurrencies() {
        //given
        Map<String, String> currencies = Map.of("PLN", "Poland", "EUR", "Euro");
        AvailableCurrencies availableCurr = new AvailableCurrencies(true, currencies);

        //when
        AvailableCurrencies returned = exchangeService.getAllCurrencies();

        //then
        assertEquals(availableCurr.getSymbols(), returned.getSymbols());
    }

    @Test
    void testExchangeCurrency() {
        //given
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("EUR");
        command.setTo("PLN");
        command.setAmount("100");
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                null, 400, null);

        when(exchangeApiClient.exchangeCurrency(any(String.class), any(String.class),
                any(String.class))).thenReturn(currencyExchange);

        //when
        CurrencyExchange returned = exchangeService.exchangeCurrency(command);

        //then
        assertEquals(currencyExchange.getResult(), returned.getResult());
    }

    @Test
    void testExchangeSend() {
        //given
        CurrencyExchangeCommand command = new CurrencyExchangeCommand();
        command.setFrom("EUR");
        command.setTo("PLN");
        command.setAmount("100");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test");
        message.setSubject("Wymiana walut " + LocalDate.now());
        message.setText("Wymieniono: " + command.getAmount() + " " + command.getFrom() +
                "\nOtrzymano: 400.0 " + command.getTo());

        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                new CurrencyExchange.Query("PLN", "EUR", "100"), 400, null);

        when(exchangeApiClient.exchangeCurrency(any(String.class), any(String.class), any(String.class)))
                .thenReturn(currencyExchange);

        //when
        CurrencyExchange returned = exchangeService.exchangeSend("test", command);

        //then
        assertEquals(currencyExchange.getResult(), returned.getResult());
        verify(emailService).sendExchangeCurrencyMessage("test", currencyExchange);
    }
}