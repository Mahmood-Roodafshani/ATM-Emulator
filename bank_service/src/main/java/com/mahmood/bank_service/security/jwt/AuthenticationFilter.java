package com.mahmood.bank_service.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.CardDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/card/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            CardDto cardDto = new ObjectMapper().readValue(request.getInputStream(), CardDto.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cardDto.getCardNumber(), cardDto.getAuthenticationValue(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read request" + e);
        }
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) {
        String token = Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(SignatureAlgorithm.HS512, "SecretKeyToGenJWTs".getBytes())
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
    }
}


