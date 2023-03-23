package pl.zajonz.exchange.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import pl.zajonz.exchange.model.CurrencyExchange;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender emailSender;

    @Test
    void testSendExchangeCurrencyMessage() {
        //given
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                new CurrencyExchange.Query("PLN", "EUR", "100"), 400, null);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test");
        message.setSubject("Wymiana walut " + LocalDate.now());
        message.setText("Wymieniono: " + currencyExchange.getQuery().getAmount() + " " +
                currencyExchange.getQuery().getFrom() + "\nOtrzymano: 400.0 " + currencyExchange.getQuery().getTo());
        //when
        emailService.sendExchangeCurrencyMessage("test", currencyExchange);

        //then
        verify(emailSender).send(message);
    }

    @Test
    void testSendSimpleMessage() {
        //given
        CurrencyExchange currencyExchange = new CurrencyExchange(true, LocalDate.now(),
                new CurrencyExchange.Query("PLN", "EUR", "100"), 400, null);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("test");
        message.setSubject("Wymiana walut " + LocalDate.now());
        message.setText("Wymieniono: " + currencyExchange.getQuery().getAmount() + " " +
                currencyExchange.getQuery().getFrom() + "\nOtrzymano: 400.0 " + currencyExchange.getQuery().getTo());
        //when
        emailService.sendExchangeCurrencyMessage("test", currencyExchange);

        //then
        verify(emailSender).send(message);
    }
}








