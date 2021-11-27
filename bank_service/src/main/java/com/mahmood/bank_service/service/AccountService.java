package com.mahmood.bank_service.service;

import model.TransactionModel;
import model.TransactionResult;

import javax.validation.constraints.NotNull;

public interface AccountService {
    TransactionResult withdraw(TransactionModel withdrawModel);

    TransactionResult deposit(TransactionModel withdrawModel);

    TransactionResult checkBalance(@NotNull String cardNumber);
}
