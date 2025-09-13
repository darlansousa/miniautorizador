package br.com.darlansilva.miniautorizador.core.usecase.card;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.exception.CardAlreadyExistisException;
import br.com.darlansilva.miniautorizador.core.exception.UserNotFoundException;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import br.com.darlansilva.miniautorizador.core.gateway.CardGateway;
import br.com.darlansilva.miniautorizador.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SaveCardAccountUseCase {

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(500);

    private final CardGateway cardGateway;
    private final UserGateway userGateway;
    private final CardAccountGateway cardAccountGateway;

    @Transactional
    public Card saveCard(Card card, String username) {
        final var user = userGateway.findBy(username).orElseThrow(UserNotFoundException::new);

        cardGateway.findBy(card.getCardNumber()).ifPresent(savedCard -> {
            throw new CardAlreadyExistisException(savedCard.getCardNumber(), savedCard.getPassword());
        });
        final var cardAccount = CardAccount.from(INITIAL_BALANCE, user, card);
        cardAccountGateway.save(cardAccount);
        return new Card(card.getId(), card.getCardNumber(), card.getPassword());
    }
}
