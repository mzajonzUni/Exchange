package pl.zajonz.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.zajonz.exchange.configuration.properties.ExchangeApiProperties;

@SpringBootApplication
@EnableConfigurationProperties({ExchangeApiProperties.class})
public class ExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeApplication.class, args);
    }

}
