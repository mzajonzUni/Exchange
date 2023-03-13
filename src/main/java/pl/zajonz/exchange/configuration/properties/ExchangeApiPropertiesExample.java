package pl.zajonz.exchange.configuration.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ExchangeApiPropertiesExample {
    @Value("${exchange.api.api-key}")
    private String apiKey;
    @Value("${exchange.api.base-url}")
    private String baseUrl;


}
