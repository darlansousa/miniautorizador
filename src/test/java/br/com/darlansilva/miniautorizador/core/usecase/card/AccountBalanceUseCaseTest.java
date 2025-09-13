package br.com.darlansilva.miniautorizador.core.usecase.card;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;

@ExtendWith(MockitoExtension.class)
class AccountBalanceUseCaseTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private AccountBalanceUseCase subject;

    @Mock
    private CardAccountGateway cardAccountGatewayMock;


    @Test
    void shouldGetBalanceByCardNumber() {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var domain = EASY_RANDOM.nextObject(CardAccount.class);

        given(cardAccountGatewayMock.findBy(username, cardNumber)).willReturn(Optional.of(domain));

        assertEquals(domain.getBalance(), subject.getBalanceBy(cardNumber, username));

        then(cardAccountGatewayMock).should().findBy(username, cardNumber);
    }

    @Test
    void shouldThrowExceptionWhenCardAccountIsNull() {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var username = EASY_RANDOM.nextObject(String.class);

        given(cardAccountGatewayMock.findBy(username, cardNumber)).willReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> subject.getBalanceBy(cardNumber, username));

        then(cardAccountGatewayMock).should().findBy(username, cardNumber);
    }



}