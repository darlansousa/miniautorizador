package br.com.darlansilva.miniautorizador.entrypoint.api.controller.cards.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.math.BigDecimal;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.exception.CardAlreadyExistisException;
import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.usecase.card.AccountBalanceUseCase;
import br.com.darlansilva.miniautorizador.core.usecase.card.SaveCardAccountUseCase;
import br.com.darlansilva.miniautorizador.entrypoint.api.config.ApiExceptionHandler;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.CardInputFormDto;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.output.CardOutputDto;

@ExtendWith(MockitoExtension.class)
class CardsControllerV1ImplTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    private static final ObjectMapper mapper =  new ObjectMapper();
    private MockMvc mvc;
    @InjectMocks
    private CardsControllerV1Impl subject;

    @Mock
    private SaveCardAccountUseCase cardsUseCaseMock;
    @Mock
    private AccountBalanceUseCase accountBalanceUseCaseMock;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(subject)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void shouldReturnBalanceByCardNumber() throws Exception {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var balance = EASY_RANDOM.nextObject(BigDecimal.class);

        given(accountBalanceUseCaseMock.getBalanceBy(cardNumber, username)).willReturn(balance);

        final var response = mvc.perform(
                        get("/cartoes/" + cardNumber)
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(balance.toString(), response.getContentAsString());

        then(accountBalanceUseCaseMock).should().getBalanceBy(cardNumber, username);
        then(cardsUseCaseMock).shouldHaveNoInteractions();
    }

    @Test
    void shouldReturn401StatusIfCardDoesNotExists() throws Exception {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);

        given(accountBalanceUseCaseMock.getBalanceBy(cardNumber, username)).willThrow(CardNotFoundException.class);

        final var response = mvc.perform(
                        get("/cartoes/" + cardNumber)
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("", response.getContentAsString());

        then(accountBalanceUseCaseMock).should().getBalanceBy(cardNumber, username);
        then(cardsUseCaseMock).shouldHaveNoInteractions();
    }

    @Test
    void shouldReturn401StatusIfNotAuthenticated() throws Exception {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);

        final var response = mvc.perform(
                        get("/cartoes/" + cardNumber)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
        assertEquals("{\"code\":\"NOT_AUTHENTICATED\",\"properties\":[]}", response.getContentAsString());

        then(accountBalanceUseCaseMock).shouldHaveNoInteractions();
        then(cardsUseCaseMock).shouldHaveNoInteractions();
    }

    @Test
    void shouldSaveNewCard() throws Exception {
        final var card = EASY_RANDOM.nextObject(Card.class);
        final var cardToSave = Card.from(card.getCardNumber(), card.getPassword());
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var input = CardInputFormDto.builder()
                .numeroCartao(card.getCardNumber())
                .senha(card.getPassword())
                .build();
        final var output = CardOutputDto.builder()
                .numeroCartao(card.getCardNumber())
                .senha(card.getPassword())
                .build();

        given(cardsUseCaseMock.saveCard(cardToSave, username)).willReturn(card);

        final var response = mvc.perform(
                        post("/cartoes")
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                                 .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(output), response.getContentAsString());

        then(cardsUseCaseMock).should().saveCard(cardToSave, username);
    }

    @Test
    void shouldNotSaveNewCardIfItExists() throws Exception {
        final var card = EASY_RANDOM.nextObject(Card.class);
        final var cardToSave = Card.from(card.getCardNumber(), card.getPassword());
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var input = CardInputFormDto.builder()
                .numeroCartao(card.getCardNumber())
                .senha(card.getPassword())
                .build();
        final var output = CardOutputDto.builder()
                .numeroCartao(input.getNumeroCartao())
                .senha(input.getSenha())
                .build();

        given(cardsUseCaseMock.saveCard(cardToSave, username)).willThrow(new CardAlreadyExistisException(
                cardToSave.getCardNumber(),
                cardToSave.getPassword()
        ));

        final var response = mvc.perform(
                        post("/cartoes")
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        assertEquals(mapper.writeValueAsString(output), response.getContentAsString());

        then(cardsUseCaseMock).should().saveCard(cardToSave, username);
    }

}