package com.mahmood.bank_service.security.jwt;

import com.mahmood.bank_service.entities.Card;
import com.mahmood.bank_service.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String cardNumber = authentication.getName();
        String auth = authentication.getCredentials().toString();

        if (Objects.nonNull(cardNumber) && Objects.nonNull(auth)) {
            Card card = cardRepository.findByCardNumber(cardNumber);
            if (!card.getAuthValue().equals(auth)) {
                throw new BadCredentialsException("Wrong authentication parameters");
            }
            return new UsernamePasswordAuthenticationToken(
                    cardNumber, auth, new ArrayList<>());
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
