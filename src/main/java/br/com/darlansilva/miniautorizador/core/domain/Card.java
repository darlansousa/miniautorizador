package br.com.darlansilva.miniautorizador.core.domain;

import java.util.Objects;

import br.com.darlansilva.miniautorizador.core.exception.InvalidPasswordException;

public class Card {
    private final Long id;
    private final String cardNumber;
    private final String password;

     public Card(Long id, String cardNumber, String password) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public static Card from(Long id, String cardNumber, String password) {
        return new Card(id, cardNumber, password);
    }

    public static Card from(String cardNumber, String password) {
        return new Card(null, cardNumber, password);
    }

    public Long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void checkIncorrect(String password, Runnable doBeforeException) {
        if (Objects.equals(password, this.getPassword())) return;
        doBeforeException.run();
        throw new InvalidPasswordException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(cardNumber, card.cardNumber) &&
                Objects.equals(password, card.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, password);
    }
}
