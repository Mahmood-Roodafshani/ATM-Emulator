package com.mahmood.bank_service.security.jwt;

import com.mahmood.bank_service.entities.Card;
import com.mahmood.bank_service.repository.CardRepository;
import enums.CardStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Authentication authenticate(Authentication authentication) {

        String cardNumber = authentication.getName();
        String auth = authentication.getCredentials().toString();

        if (Objects.nonNull(cardNumber) && Objects.nonNull(auth)) {
            Card card = cardRepository.findByCardNumber(cardNumber);
            if (card.getNumberOfLoginTries() == 3 || CardStatus.BLOCKED.equals(card.getCardStatus())) {
                throw new LockedException("Card is BLOCKED!");
            }
            if (!card.getAuthValue().equals(auth)) {
                card.setNumberOfLoginTries((byte) (card.getNumberOfLoginTries() + 1));
                if (card.getNumberOfLoginTries() == 3)
                    card.setCardStatus(CardStatus.BLOCKED);
                cardRepository.save(card);
                throw new BadCredentialsException("Wrong authentication parameter. You have " + (3 - card.getNumberOfLoginTries()) + " more tried left.");
            }
            return new UsernamePasswordAuthenticationToken(
                    cardNumber, auth, new ArrayList<>());
        } else {
            throw new BadCredentialsException("Card number or authentication parameter not provided.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
