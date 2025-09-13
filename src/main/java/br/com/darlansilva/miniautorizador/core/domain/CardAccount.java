package br.com.darlansilva.miniautorizador.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class CardAccount {
    private final Long id;
    private final BigDecimal balance;
    private final User user;
    private final Card card;

     public CardAccount(Long id, BigDecimal balance, User user, Card card) {
        this.id = id;
        this.balance = balance;
        this.user = user;
        this.card = card;
    }

    public static CardAccount from(Long id, BigDecimal balance, User user, Card card) {
        return new CardAccount(id, balance, user, card);
    }

    public static CardAccount from(BigDecimal balance, User user, Card card) {
        return new CardAccount(null,balance, user, card);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CardAccount that = (CardAccount) o;
        return Objects.equals(id, that.id) && Objects.equals(balance, that.balance) &&
                Objects.equals(user, that.user) && Objects.equals(card, that.card);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, user, card);
    }
}
