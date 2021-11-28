package com.mahmood.atm_service.restclient;

import model.CardDto;

public class MyContextHolder {
    private static MyContextHolder ourInstance = new MyContextHolder();

    private String token;
    private CardDto card;

    public CardDto getCard() {
        return card;
    }

    public void setCard(CardDto card) {
        this.card = card;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static MyContextHolder getInstance() {
        return ourInstance;
    }

    private MyContextHolder() {
    }
}
