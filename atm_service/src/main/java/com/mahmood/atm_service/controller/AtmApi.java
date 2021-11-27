package com.mahmood.atm_service.controller;

import com.mahmood.atm_service.SecurityInterceptor;
import enums.CardStatus;
import model.CardDto;
import model.TransactionModel;
import model.TransactionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/")
public class AtmApi {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Value("${bankServiceUrl}")
    private String url;
    private CardDto card;
//    @HystrixCommand(fallbackMethod = "logIn")

    @PostMapping("withdraw")
    public TransactionResult withdraw(TransactionModel transactionModel) {
        return restTemplate.postForObject(url + "/account/withdraw", transactionModel, TransactionResult.class);
    }

    @PostMapping("deposit")
    public TransactionResult deposit(TransactionModel transactionModel) {
        return restTemplate.postForObject(url + "/account/deposit", transactionModel, TransactionResult.class);
    }

    @GetMapping("balance/{cardNumber}")
    public TransactionResult checkBalance(@PathVariable String cardNumber) {
        return restTemplate.getForObject(url + "/account/balance/" + cardNumber, TransactionResult.class);
    }

    @GetMapping("/card/validateCardNumber/{cardNumber}")
    public CardStatus validateCardNumber(@PathVariable @NotNull String cardNumber) {
        card = restTemplate.getForObject(url + "/card/validateCardNumber/" + cardNumber, CardDto.class);
        return card.getCardStatus();
    }

    @PostMapping("/card/login/{authenticationValue}")
    public TransactionResult login(@PathVariable @NotNull String authenticationValue) {
        card = new CardDto();


        if (card.getNumberOfLoginTries() == 3) {
            throw new RuntimeException("Card is BLOCKED!");
        }
        card.setAuthenticationValue(authenticationValue);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", "john");
        params.add("password", "123");


        card.setCardNumber("1");
        card.setAuthenticationValue("1");

        HttpEntity<String> response = restTemplate.exchange(url + "/card/login", HttpMethod.POST, new HttpEntity<>(card), String.class);
        String resultString = response.getBody();
        restTemplate.
                securityInterceptor response.getHeaders().get("Authorization");
        TransactionResult transactionResult = restTemplate.postForObject(url + "/card/login", card, TransactionResult.class);
        card = new CardDto();
        return transactionResult;
    }

    @PostMapping("test")
    public void checkBalance() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("username", "john");
        params.add("password", "123");
        restTemplate.postForObject("http://localhost:8080/login", params, Object.class);
    }

}
