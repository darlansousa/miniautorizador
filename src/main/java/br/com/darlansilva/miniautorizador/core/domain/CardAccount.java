package br.com.darlansilva.miniautorizador.core.domain;

import java.math.BigDecimal;

public class CardAccount {
    private final Long id;
    private final BigDecimal balance;
    private final User user;
    private final Card card;

    private CardAccount(Long id, BigDecimal balance, User user, Card card) {
        this.id = id;
        this.balance = balance;
        this.user = user;
        this.card = card;
    }

    public static CardAccount newCardAccount(BigDecimal balance, User user, Card card) {
        return new CardAccount(null, balance, user, card);
    }

    public static CardAccount from(Long id, BigDecimal balance, User user, Card card) {
        return new CardAccount(id, balance, user, card);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public Card getCard() {
        return card;
    }
}
