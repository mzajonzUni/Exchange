package pl.zajonz.exchange.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.model.AvailableCurrencies;

@Configuration
public class AvailableCurrenciesConfiguration {

    @Bean
    public AvailableCurrencies availableCurrencies(ExchangeApiClient client) {
        return client.getCurrencies();
    }
}
