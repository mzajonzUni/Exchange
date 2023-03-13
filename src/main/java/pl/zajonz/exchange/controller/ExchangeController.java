package pl.zajonz.exchange.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zajonz.exchange.model.TestModel;
import pl.zajonz.exchange.service.ExchangeServiceImpl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange")
public class ExchangeController {

    private final ExchangeServiceImpl exchangeService;

    @GetMapping("/test")
    public TestModel getProperties(){
        return exchangeService.test();
    }

}
