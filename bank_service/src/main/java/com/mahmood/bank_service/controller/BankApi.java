package com.mahmood.bank_service.controller;

import com.mahmood.bank_service.service.AccountService;
import com.mahmood.bank_service.service.CardService;
import lombok.RequiredArgsConstructor;
import model.CardDto;
import model.TransactionModel;
import model.TransactionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BankApi {

    private final CardService cardService;
    private final AccountService accountService;

    @PostMapping("/account/withdraw")
    public ResponseEntity<TransactionResult> withdraw(@RequestBody TransactionModel model) {
        return ResponseEntity.ok(accountService.withdraw(model));
    }

    @PostMapping("/account/deposit")
    public ResponseEntity<TransactionResult> deposit(@RequestBody TransactionModel model) {
        return ResponseEntity.ok(accountService.deposit(model));
    }

    @GetMapping("/account/balance/{cardNumber}")
    public ResponseEntity<TransactionResult> checkBalance(@PathVariable @NotNull String cardNumber) {
        return ResponseEntity.ok(accountService.checkBalance(cardNumber));
    }


    @GetMapping("/card/validateCardNumber/{cardNumber}")
    public ResponseEntity<CardDto> validateCardNumber(@PathVariable @NotNull String cardNumber) {
        return ResponseEntity.ok(cardService.validateCardNumber(cardNumber));
    }

//    @PostMapping("/card/login")
//    public ResponseEntity<TransactionResult> login(@RequestBody CardDto model) {
//        return ResponseEntity.ok(/*accountService.deposit(model)*/);
//    }

//    @PostMapping("/card/chooseAuthMethod")
//    public void chooseAuthMethod(@RequestBody AuthMethod authMethod){
//        cardService.checkBalance(cardNumber)
//        ResponseEntity.ok();
//    }
}
