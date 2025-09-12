package br.com.darlansilva.miniautorizador.core.domain;

public class Card {
    private final Long id;
    private final String cardNumber;
    private final String password;
    private final CardAccount cardAccount;

     public Card(Long id, String cardNumber, String password, CardAccount cardAccount) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.password = password;
        this.cardAccount = cardAccount;
    }

    public static Card from(Long id, String cardNumber, String password, CardAccount cardAccount) {
        return new Card(id, cardNumber, password, cardAccount);
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

    public CardAccount getCardAccount() {
        return cardAccount;
    }
}
