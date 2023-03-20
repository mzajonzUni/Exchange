package pl.zajonz.exchange.configuration;

import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.zajonz.exchange.adapter.LocalDateTypeAdapter;
import pl.zajonz.exchange.client.ExchangeApiClient;
import pl.zajonz.exchange.configuration.properties.ExchangeApiProperties;

import java.time.LocalDate;

@Configuration
public class ExchangeApiClientConfiguration {

    @Bean
    public ExchangeApiClient exchangeApiClient(ExchangeApiProperties properties) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());
        GsonDecoder decoder = new GsonDecoder(gsonBuilder.create());

        return Feign.builder()
                .requestInterceptor(template -> template.header("apikey", properties.getApiKey()))
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(decoder)
                .logger(new Slf4jLogger(ExchangeApiClient.class))
                .logLevel(Logger.Level.FULL)
                .target(ExchangeApiClient.class, properties.getBaseUrl());
    }
}
