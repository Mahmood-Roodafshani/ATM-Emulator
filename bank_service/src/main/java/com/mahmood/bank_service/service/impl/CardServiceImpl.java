package com.mahmood.bank_service.service.impl;

import com.mahmood.bank_service.entities.Card;
import com.mahmood.bank_service.repository.CardRepository;
import com.mahmood.bank_service.service.CardService;
import model.CardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public CardDto validateCardNumber(String cardNumber) {
        Card card = cardRepository.findByCardNumber(cardNumber);
        if (Objects.isNull(card)) {
            throw new RuntimeException("Invalid card number");
        }
        //TODO add MapStruct
        return new CardDto(card.getCardNumber(), card.getCardStatus(), card.getNumberOfLoginTries(), card.getAuthMethod(), null);
    }
}
