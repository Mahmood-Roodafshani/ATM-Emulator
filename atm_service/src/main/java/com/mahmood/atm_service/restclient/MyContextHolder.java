package com.mahmood.atm_service.restclient;

public class MyContextHolder {
    private static MyContextHolder ourInstance = new MyContextHolder();

    private String token;

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
