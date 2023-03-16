package pl.zajonz.exchange.service;

import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.configuration.properties.ExchangeApiProperties;
import pl.zajonz.exchange.model.AvailableCurrencies;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeApiProperties properties;
//    private final ExchangeApiClient exchangeApiClient;
    private final AvailableCurrencies availableCurrencies;

    @Override
    public AvailableCurrencies getAllCurrencies() {
        return availableCurrencies;
    }
}
