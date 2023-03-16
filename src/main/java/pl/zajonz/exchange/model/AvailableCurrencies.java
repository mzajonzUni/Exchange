package pl.zajonz.exchange.model;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class AvailableCurrencies {

    private Boolean success;
    private Map<String,String> symbols;
}
