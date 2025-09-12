package br.com.darlansilva.miniautorizador.core.domain;

import java.util.UUID;

public class TransactionEvent {
    private final UUID id;
    private final String username;
    private final String cardNumber;
    private final EventType eventType;
    private final Long amount;

    public TransactionEvent(UUID id, String username, String cardNumber, EventType eventType, Long amount) {
        this.id = id;
        this.username = username;
        this.cardNumber = cardNumber;
        this.eventType = eventType;
        this.amount = amount;
    }

    public static TransactionEvent from(UUID id, String username, String cardNumber, EventType eventType, Long amount) {
        return new TransactionEvent(id, username, cardNumber, eventType, amount);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Long getAmount() {
        return amount;
    }
}
