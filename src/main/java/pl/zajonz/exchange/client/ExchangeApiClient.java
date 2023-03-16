package pl.zajonz.exchange.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import pl.zajonz.exchange.model.AvailableCurrencies;

public interface ExchangeApiClient {

    @RequestLine("GET /symbols")
    @Headers("apikey: {apiKey}")
    AvailableCurrencies getCurrencies(@Param("apiKey") String apiKey);

//    @RequestLine("GET")
//    List<BookResource> findAll();
//
//    @RequestLine("POST")
//    @Headers("Content-Type: application/json")
//    void create(Book book);
}
