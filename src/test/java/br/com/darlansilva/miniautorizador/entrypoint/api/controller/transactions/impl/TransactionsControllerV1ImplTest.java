package br.com.darlansilva.miniautorizador.entrypoint.api.controller.transactions.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
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
import br.com.darlansilva.miniautorizador.core.domain.TransactionStatus;
import br.com.darlansilva.miniautorizador.core.exception.InvalidPasswordException;
import br.com.darlansilva.miniautorizador.core.usecase.transaction.PaymentTransactionUseCase;
import br.com.darlansilva.miniautorizador.entrypoint.api.config.ApiExceptionHandler;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.TransactionInputFormDto;

@ExtendWith(MockitoExtension.class)
class TransactionsControllerV1ImplTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();
    private static final ObjectMapper mapper =  new ObjectMapper();
    private MockMvc mvc;
    @InjectMocks
    private TransactionsControllerV1Impl subject;

    @Mock
    private PaymentTransactionUseCase paymentTransactionUseCaseMock;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(subject)
                .setControllerAdvice(new ApiExceptionHandler())
                .build();
    }

    @Test
    void shouldProcessPayment() throws Exception {
        final var returnStatus = TransactionStatus.OK;
        final var card = EASY_RANDOM.nextObject(Card.class);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var input = TransactionInputFormDto.builder()
                .numeroCartao(card.getCardNumber())
                .senhaCartao(card.getPassword())
                .valor(BigDecimal.TEN)
                .build();

        given(paymentTransactionUseCaseMock.processPayment(
                input.getNumeroCartao(),
                input.getSenhaCartao(),
                input.getValor(),
                username
        )).willReturn(returnStatus);

        final var response = mvc.perform(
                        post("/transacoes")
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(returnStatus.getValue(), response.getContentAsString());

        then(paymentTransactionUseCaseMock).should().processPayment(
                input.getNumeroCartao(),
                input.getSenhaCartao(),
                input.getValor(),
                username
        );
    }


    @Test
    void shouldReturnErrorForUnauthorizedTransaction() throws Exception {
        final var returnStatus = TransactionStatus.INVALID_PASSWORD;
        final var card = EASY_RANDOM.nextObject(Card.class);
        final var username = EASY_RANDOM.nextObject(String.class);
        final var password = EASY_RANDOM.nextObject(String.class);
        final var input = TransactionInputFormDto.builder()
                .numeroCartao(card.getCardNumber())
                .senhaCartao(card.getPassword())
                .valor(BigDecimal.TEN)
                .build();

        given(paymentTransactionUseCaseMock.processPayment(
                input.getNumeroCartao(),
                input.getSenhaCartao(),
                input.getValor(),
                username
        )).willThrow(new InvalidPasswordException());

        final var response = mvc.perform(
                        post("/transacoes")
                                .principal(() -> username)
                                .with(user(username).password(password))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), response.getStatus());
        assertEquals(returnStatus.getValue(), response.getContentAsString());

        then(paymentTransactionUseCaseMock).should().processPayment(
                input.getNumeroCartao(),
                input.getSenhaCartao(),
                input.getValor(),
                username
        );
    }

}