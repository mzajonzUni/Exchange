package pl.zajonz.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.service.ExchangeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/test")
    public AvailableCurrencies getProperties(){
        return exchangeService.getAllCurrencies();
    }

    // TODO: 16.03.2023 dodać endpoint który przyjmie zapytanie o wymianę walut
    //  - przyjmujemy pola from, to, amount (w formie body Query)


}
