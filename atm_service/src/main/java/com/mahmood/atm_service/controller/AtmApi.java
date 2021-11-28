package com.mahmood.atm_service.controller;

import com.mahmood.atm_service.restclient.MyContextHolder;
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

    @GetMapping("/card/validateCardNumber/{cardNumber}")
    public CardStatus validateCardNumber(@PathVariable @NotNull String cardNumber) {
        MyContextHolder.getInstance().setCard(restTemplate.getForObject(url + "/card/validateCardNumber/" + cardNumber, CardDto.class));
        return MyContextHolder.getInstance().getCard().getCardStatus();
    }

    @PostMapping("/card/login/{authenticationValue}")
    public void login(@PathVariable @NotNull String authenticationValue, HttpServletRequest request) {
        if (MyContextHolder.getInstance().getCard().getCardStatus().equals(CardStatus.BLOCKED) || MyContextHolder.getInstance().getCard().getNumberOfLoginTries() == 3) {
            throw new RuntimeException("Card is BLOCKED!");
        }
        MyContextHolder.getInstance().getCard().setAuthenticationValue(authenticationValue);

        HttpEntity<String> response = restTemplate.exchange(url + "/card/login", HttpMethod.POST, new HttpEntity<>(MyContextHolder.getInstance().getCard()), String.class);
        List<String> authorization = response.getHeaders().get("Authorization");
        MyContextHolder.getInstance().setToken(authorization.size() > 0 ? authorization.get(0) : null);
        MyContextHolder.getInstance().setCard(new CardDto());
    }


    //    @HystrixCommand(fallbackMethod = "validateCardNumber")
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

}
