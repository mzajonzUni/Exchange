package pl.zajonz.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.zajonz.exchange.model.AvailableCurrencies;
import pl.zajonz.exchange.model.CurrencyExchange;
import pl.zajonz.exchange.model.command.CurrencyExchangeCommand;
import pl.zajonz.exchange.service.ExchangeService;

import javax.validation.Valid;

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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/convert")
    public CurrencyExchange exchangeCurrency(@RequestBody @Valid CurrencyExchangeCommand command) {
        return exchangeService.exchangeCurrency(command);
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{email}")
    public CurrencyExchange exchangeSend(@PathVariable String email,
                                             @RequestBody @Valid CurrencyExchangeCommand command){
        return exchangeService.exchangeSend(email,command);
    }
}
