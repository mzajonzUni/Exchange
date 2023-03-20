package pl.zajonz.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class CurrencyExchange {
    private Boolean success;
    private LocalDate date;
    private double result;
    private CurrencyExchangeInfo info;

    @Getter
    @AllArgsConstructor
    public static class CurrencyExchangeInfo {
        long timestamp;
        double rate;
    }
}
