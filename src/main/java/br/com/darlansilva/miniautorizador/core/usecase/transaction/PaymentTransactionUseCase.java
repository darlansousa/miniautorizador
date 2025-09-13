package br.com.darlansilva.miniautorizador.core.usecase.transaction;


import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.CARD_NOT_FOUND;
import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.INSUFFICIENT_FUNDS;
import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.INVALID_PASSWORD;
import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.OK;
import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.domain.TransactionStatus;
import br.com.darlansilva.miniautorizador.core.domain.TransactionStatusEvent;
import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.exception.InvalidPasswordException;
import br.com.darlansilva.miniautorizador.core.exception.InsufficientFoundsException;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import br.com.darlansilva.miniautorizador.core.gateway.CardGateway;
import br.com.darlansilva.miniautorizador.core.gateway.TransactionStatusPublisherGateway;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentTransactionUseCase {

    private final CardGateway cardGateway;
    private final CardAccountGateway cardAccountGateway;
    private final TransactionStatusPublisherGateway transactionStatusPublisherGateway;

    @Transactional
    public TransactionStatus processPayment(String cardNumber, String password, BigDecimal value, String username) {

        cardGateway.findBy(cardNumber, username).ifPresentOrElse(card -> {
            checkPassword(password, value, username, card);
            checkAvailableFounds(cardNumber, value, username, card);
        }, () -> {
            sendEvent(username, value, CARD_NOT_FOUND);
            throw new CardNotFoundException();
        });

        sendEvent(username, value, OK);
        return OK;
    }

    private void checkAvailableFounds(String cardNumber, BigDecimal value, String username, Card card) {
        cardAccountGateway
                .findBy(username, cardNumber)
                .ifPresent(account -> update(value, account, card));
    }

    private void checkPassword(String password, BigDecimal value, String username, Card card) {
        if (!card.getPassword().equals(password)) {
            sendEvent(username, value, INVALID_PASSWORD);
            throw new InvalidPasswordException();
        }
    }

    private void sendEvent(String username, BigDecimal value, TransactionStatus status) {
        transactionStatusPublisherGateway.publish(TransactionStatusEvent.builder()
                                                          .transactionId(UUID.randomUUID())
                                                          .username(username)
                                                          .status(status)
                                                          .amount(value)
                                                          .build());
    }

    private void update(BigDecimal value, CardAccount account, Card card) {
        if (account.getBalance().compareTo(value) < 0){
            sendEvent(account.getUser().getUsername(), value, INSUFFICIENT_FUNDS);
            throw new InsufficientFoundsException();
        }

        cardAccountGateway.save(
                CardAccount.from(
                        account.getId(),
                        account.getBalance().subtract(value),
                        account.getUser(),
                        card
                )
        );
    }
}
