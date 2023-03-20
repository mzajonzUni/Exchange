package pl.zajonz.exchange.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;
import pl.zajonz.exchange.service.ExchangeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/currencies")
    public AvailableCurrencies getAllCurrencies(){
        return exchangeService.getAllCurrencies();
    }

    // TODO: 16.03.2023 dodać endpoint który przyjmie zapytanie o wymianę walut
    //  - przyjmujemy pola from, to, amount (w formie body Query)

    @PostMapping("/convert")
    public CurrencyExchange exchangeCurrency(@RequestBody @Valid CurrencyExchangeCommand command) {
        return exchangeService.exchangeCurrency(command);
    }
}
