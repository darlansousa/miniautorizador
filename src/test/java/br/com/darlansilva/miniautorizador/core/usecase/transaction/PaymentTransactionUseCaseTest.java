package br.com.darlansilva.miniautorizador.core.usecase.transaction;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mockStatic;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.domain.TransactionStatus;
import br.com.darlansilva.miniautorizador.core.domain.TransactionStatusEvent;
import br.com.darlansilva.miniautorizador.core.domain.User;
import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.exception.InsufficientFoundsException;
import br.com.darlansilva.miniautorizador.core.exception.InvalidPasswordException;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import br.com.darlansilva.miniautorizador.core.gateway.CardGateway;
import br.com.darlansilva.miniautorizador.core.gateway.TransactionStatusPublisherGateway;

@ExtendWith(MockitoExtension.class)
class PaymentTransactionUseCaseTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    private static MockedStatic<UUID> mockUuid;
    private static final UUID FIXED_UUID = UUID.fromString("12345678-1234-1234-1234-123456789012");



    @InjectMocks
    private PaymentTransactionUseCase subject;

    @Mock
    private CardGateway cardGatewayMock;
    @Mock
    private CardAccountGateway cardAccountGatewayMock;
    @Mock
    private TransactionStatusPublisherGateway transactionStatusPublisherGatewayMock;

    @BeforeEach
    void setUp() {
        mockUuid = mockStatic(UUID.class);
        mockUuid.when(UUID::randomUUID).thenReturn(FIXED_UUID);
    }

    @AfterEach
    void closeResources() {
        mockUuid.close();
    }

    @Test
    void shouldProcessPaymentOK() {
        final var balance = BigDecimal.valueOf(500);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var card = Card.from(cardNumber, password);
        final var user = EASY_RANDOM.nextObject(User.class);
        final var cardAccount = CardAccount.from(1L, balance, user, card);

        given(cardGatewayMock.findBy(cardNumber, username)).willReturn(Optional.of(card));
        given(cardAccountGatewayMock.findBy(username, cardNumber)).willReturn(Optional.of(cardAccount));
        mockUuid.when(UUID::randomUUID).thenReturn(FIXED_UUID);


        assertEquals(TransactionStatus.OK, subject.processPayment(cardNumber, password, BigDecimal.TEN, username));

        final var expectedCardAccount = CardAccount.from(1L, balance.subtract(BigDecimal.TEN), user, card);

        then(cardGatewayMock).should().findBy(cardNumber, username);
        then(cardAccountGatewayMock).should().findBy(username, cardNumber);
        then(cardAccountGatewayMock).should().save(expectedCardAccount);
        then(transactionStatusPublisherGatewayMock).should().publish(TransactionStatusEvent.builder()
                                                                             .transactionId(FIXED_UUID)
                                                                             .username(username)
                                                                             .status(TransactionStatus.OK)
                                                                             .amount(BigDecimal.TEN)
                                                                             .build());



    }

    @Test
    void shouldProcessPaymentInvalidPassword() {
        final var username = EASY_RANDOM.nextObject(String.class);
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var differentPassword = EASY_RANDOM.nextObject(String.class);
        final var card = Card.from(cardNumber, differentPassword);

        given(cardGatewayMock.findBy(cardNumber, username)).willReturn(Optional.of(card));
        mockUuid.when(UUID::randomUUID).thenReturn(FIXED_UUID);

        assertThrows(InvalidPasswordException.class,
                     () -> subject.processPayment(cardNumber, password, BigDecimal.TEN, username)
        );

        then(cardGatewayMock).should().findBy(cardNumber, username);
        then(cardAccountGatewayMock).shouldHaveNoInteractions();
        then(transactionStatusPublisherGatewayMock).should().publish(TransactionStatusEvent.builder()
                                                                             .transactionId(FIXED_UUID)
                                                                             .username(username)
                                                                             .status(TransactionStatus.INVALID_PASSWORD)
                                                                             .amount(BigDecimal.TEN)
                                                                             .build());



    }

    @Test
    void shouldProcessPaymentWithInsufficientFounds() {
        final var balance = BigDecimal.valueOf(5);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var userId = EASY_RANDOM.nextObject(Long.class);
        final var card = Card.from(cardNumber, password);
        final var user = User.from(userId, username, password, List.of());
        final var cardAccount = CardAccount.from(balance, user, card);

        given(cardGatewayMock.findBy(cardNumber, username)).willReturn(Optional.of(card));
        given(cardAccountGatewayMock.findBy(username, cardNumber)).willReturn(Optional.of(cardAccount));
        mockUuid.when(UUID::randomUUID).thenReturn(FIXED_UUID);

        assertThrows(InsufficientFoundsException.class,
                     () -> subject.processPayment(cardNumber, password, BigDecimal.TEN, username)
        );

        then(cardGatewayMock).should().findBy(cardNumber, username);
        then(cardAccountGatewayMock).should().findBy(username, cardNumber);
        then(cardAccountGatewayMock).shouldHaveNoMoreInteractions();
        then(transactionStatusPublisherGatewayMock).should().publish(TransactionStatusEvent.builder()
                                                                             .transactionId(FIXED_UUID)
                                                                             .username(username)
                                                                             .status(TransactionStatus.INSUFFICIENT_FUNDS)
                                                                             .amount(BigDecimal.TEN)
                                                                             .build());



    }

    @Test
    void shouldNotProcessPaymentWithCardNotFound() {
        final var username = EASY_RANDOM.nextObject(String.class);
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);

        given(cardGatewayMock.findBy(cardNumber, username)).willReturn(Optional.empty());
        mockUuid.when(UUID::randomUUID).thenReturn(FIXED_UUID);

        assertThrows(CardNotFoundException.class,
                     () -> subject.processPayment(cardNumber, password, BigDecimal.TEN, username)
        );

        then(cardGatewayMock).should().findBy(cardNumber, username);
        then(cardAccountGatewayMock).shouldHaveNoInteractions();
        then(transactionStatusPublisherGatewayMock).should().publish(TransactionStatusEvent.builder()
                                                                             .transactionId(FIXED_UUID)
                                                                             .username(username)
                                                                             .status(TransactionStatus.CARD_NOT_FOUND)
                                                                             .amount(BigDecimal.TEN)
                                                                             .build());



    }

}