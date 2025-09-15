package br.com.darlansilva.miniautorizador.core.usecase.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import java.math.BigDecimal;
import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import br.com.darlansilva.miniautorizador.core.gateway.CardGateway;
import br.com.darlansilva.miniautorizador.core.gateway.UserGateway;

@ExtendWith(MockitoExtension.class)
class SaveCardAccountUseCaseTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private SaveCardAccountUseCase subject;

    @Mock
    private CardGateway cardGatewayMock;
    @Mock
    private UserGateway userGatewayMock;
    @Mock
    private CardAccountGateway cardAccountGatewayMock;


    @Test
    void shouldGetBalanceByCardNumber() {
        final var card = EASY_RANDOM.nextObject(Card.class);
        final var cardNumber = card.getCardNumber();
        final var cardAccount = EASY_RANDOM.nextObject(CardAccount.class);
        final var username = cardAccount.getUser().getUsername();
        final var user = cardAccount.getUser();

        given(userGatewayMock.findBy(username)).willReturn(Optional.of(user));
        given(cardGatewayMock.findBy(cardNumber, username)).willReturn(Optional.empty());

        assertEquals(card, subject.saveCard(card, username));

        then(cardAccountGatewayMock).should().save(CardAccount.from(BigDecimal.valueOf(500), user, card));
        then(cardGatewayMock).should().findBy(cardNumber, username);
        then(userGatewayMock).should().findBy(username);
    }

}