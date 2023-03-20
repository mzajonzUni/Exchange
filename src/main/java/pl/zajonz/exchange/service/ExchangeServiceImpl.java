package pl.zajonz.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final AvailableCurrencies availableCurrencies;
    private final ExchangeApiClient exchangeApiClient;

    @Override
    public AvailableCurrencies getAllCurrencies() {
        return availableCurrencies;
    }

    @Override
    public CurrencyExchange exchangeCurrency(CurrencyExchangeCommand command) {
        return exchangeApiClient.exchangeCurrency(command.getFrom(), command.getTo(), command.getAmount());
    }
}
