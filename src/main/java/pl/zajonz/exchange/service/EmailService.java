package pl.zajonz.exchange.service;

import pl.zajonz.exchange.model.CurrencyExchange;

public interface EmailService {

    void sendExchangeCurrencyMessage(String email, CurrencyExchange currencyExchange);

    void sendSimpleMessage(String to, String subject, String text);

}
