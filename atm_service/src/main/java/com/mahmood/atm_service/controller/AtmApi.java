package com.mahmood.atm_service.controller;

import com.mahmood.atm_service.restclient.MyContextHolder;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import enums.CardStatus;
import model.CardDto;
import model.TransactionModel;
import model.TransactionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/")
public class AtmApi {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${bankServiceUrl}")
    private String url;
    private CardDto card;

    @HystrixCommand(fallbackMethod = "login")
    @PostMapping("withdraw")
    public TransactionResult withdraw(@RequestBody TransactionModel transactionModel) {
        return restTemplate.postForObject(url + "/account/withdraw", transactionModel, TransactionResult.class);
    }

    @PostMapping("deposit")
    public TransactionResult deposit(@RequestBody TransactionModel transactionModel) {
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
    public void login(@PathVariable @NotNull String authenticationValue, HttpServletRequest request) {
        card = new CardDto();

        if (card.getCardStatus().equals(CardStatus.BLOCKED) || card.getNumberOfLoginTries() == 3) {
            throw new RuntimeException("Card is BLOCKED!");
        }
        card.setAuthenticationValue(authenticationValue);

        HttpEntity<String> response = restTemplate.exchange(url + "/card/login", HttpMethod.POST, new HttpEntity<>(card), String.class);
        List<String> authorization = response.getHeaders().get("Authorization");
        MyContextHolder.getInstance().setToken(authorization.size() > 0 ? authorization.get(0) : null);
        card = new CardDto();
    }


}
