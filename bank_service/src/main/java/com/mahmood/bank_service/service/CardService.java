package com.mahmood.bank_service.service;

import model.CardDto;

public interface CardService {
    CardDto validateCardNumber(String cardNumber);
}
