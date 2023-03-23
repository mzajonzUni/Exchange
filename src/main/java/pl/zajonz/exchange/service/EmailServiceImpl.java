package pl.zajonz.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.zajonz.exchange.model.CurrencyExchange;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Override
    public void sendExchangeCurrencyMessage(String email, CurrencyExchange currencyExchange) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Wymiana walut " + currencyExchange.getDate());
        message.setText("Wymieniono: " + currencyExchange.getQuery().getAmount() +
                " " + currencyExchange.getQuery().getFrom() +
                "\nOtrzymano: " + currencyExchange.getResult() +
                " " + currencyExchange.getQuery().getTo());
        emailSender.send(message);
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
