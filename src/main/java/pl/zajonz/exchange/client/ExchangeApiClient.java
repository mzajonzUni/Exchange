package pl.zajonz.exchange.client;

import feign.Param;
import feign.RequestLine;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;

public interface ExchangeApiClient {

    @RequestLine("GET /symbols")
    AvailableCurrencies getCurrencies();

    @RequestLine("GET /convert?from={from}&to={to}&amount={amount}")
    CurrencyExchange exchangeCurrency(@Param("from") String from, @Param("to") String to, @Param("amount") String amount);
}

// kowalki733 / kowalki733!@#