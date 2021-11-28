package com.mahmood.bank_service.service.impl;

import com.mahmood.bank_service.entities.Account;
import com.mahmood.bank_service.entities.Card;
import com.mahmood.bank_service.repository.AccountRepository;
import com.mahmood.bank_service.repository.CardRepository;
import com.mahmood.bank_service.service.AccountService;
import enums.CardStatus;
import enums.OperationStatus;
import model.TransactionModel;
import model.TransactionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public TransactionResult withdraw(TransactionModel transactionModel) {
        Card card = cardRepository.findByCardNumber(transactionModel.getCardNumber());
        if (Objects.isNull(card)) {
            return new TransactionResult(OperationStatus.FAILED, "Card not found.");
        }

        if (CardStatus.BLOCKED.equals(card.getCardStatus())){
            return new TransactionResult(OperationStatus.FAILED, "Card is Blocked.");
        }

        Account account = card.getAccount();
        if (account.getBalance().compareTo(transactionModel.getAmount()) < 0) {
            return new TransactionResult(OperationStatus.FAILED, "Not enough balance.");
        }
        account.setBalance(account.getBalance().subtract(transactionModel.getAmount()));
        accountRepository.save(account);
        return new TransactionResult(OperationStatus.SUCCESS, "Success.");
    }

    @Override
    public TransactionResult deposit(TransactionModel transactionModel) {
        Card card = cardRepository.findByCardNumber(transactionModel.getCardNumber());
        if (Objects.isNull(card)) {
            return new TransactionResult(OperationStatus.FAILED, "Card not found.");
        }
        if (CardStatus.BLOCKED.equals(card.getCardStatus())){
            return new TransactionResult(OperationStatus.FAILED, "Card is Blocked.");
        }

        Account account = card.getAccount();
        account.setBalance(account.getBalance().add(transactionModel.getAmount()));
        accountRepository.save(account);
        return new TransactionResult(OperationStatus.SUCCESS, "Success.");
    }

    @Override
    public TransactionResult checkBalance(@NotNull String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (Objects.isNull(card)) {
            return new TransactionResult(OperationStatus.FAILED, "Card not found.");
        }
        if (CardStatus.BLOCKED.equals(card.getCardStatus())){
            return new TransactionResult(OperationStatus.FAILED, "Card is Blocked.");
        }
        Account account = card.getAccount();
        return new TransactionResult(OperationStatus.SUCCESS, String.valueOf(account.getBalance()));
    }
}
