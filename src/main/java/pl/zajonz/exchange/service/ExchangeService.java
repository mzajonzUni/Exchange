package pl.zajonz.exchange.service;

import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;

public interface ExchangeService {

    AvailableCurrencies getAllCurrencies();
    CurrencyExchange exchangeCurrency(CurrencyExchangeCommand command);
}
