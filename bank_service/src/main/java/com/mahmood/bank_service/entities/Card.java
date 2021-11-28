package com.mahmood.bank_service.entities;

import enums.AuthMethod;
import enums.CardStatus;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Card implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Pattern(regexp = "[\\d]")
    @Column(unique = true, nullable = false, length = 16)
    private String cardNumber;
    @NotNull
    private CardStatus cardStatus;
    @ManyToOne
    @JoinColumn(name = "account_id", insertable = false, updatable = false, nullable = false)
    private Account account;
    @Max(3)
    private byte numberOfLoginTries;
    @NotNull
    private AuthMethod authMethod;
    @NotNull
    private String authValue;
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Token> tokens;

    public Card() {
    }

    public AuthMethod getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(AuthMethod authMethod) {
        this.authMethod = authMethod;
    }

    public String getAuthValue() {
        return authValue;
    }

    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public byte getNumberOfLoginTries() {
        return numberOfLoginTries;
    }

    public void setNumberOfLoginTries(byte numberOfLoginTries) {
        this.numberOfLoginTries = numberOfLoginTries;
    }
}
