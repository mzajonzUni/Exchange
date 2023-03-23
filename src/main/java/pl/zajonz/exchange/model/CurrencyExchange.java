package pl.zajonz.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CurrencyExchange {
    private Boolean success;
    private LocalDate date;
    private Query query;
    private double result;
    private CurrencyExchangeInfo info;

    @Getter
    @AllArgsConstructor
    public static class CurrencyExchangeInfo {
        long timestamp;
        double rate;
    }

    @Getter
    @AllArgsConstructor
    public static class Query {
        String to;
        String from;
        String amount;
    }
}
