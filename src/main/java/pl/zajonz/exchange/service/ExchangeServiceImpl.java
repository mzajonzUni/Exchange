package pl.zajonz.exchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajonz.exchange.configuration.properties.ExchangeApiProperties;
import pl.zajonz.exchange.model.TestModel;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final ExchangeApiProperties properties;

    @Override
    public TestModel test() {
        return new TestModel(properties.getApiKey(), properties.getBaseUrl());
    }
}
