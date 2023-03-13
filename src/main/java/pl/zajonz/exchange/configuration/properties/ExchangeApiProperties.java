package pl.zajonz.exchange.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "exchange.api")
@Setter
@Getter
public class ExchangeApiProperties {

    private String apiKey;
    private String baseUrl;
}
