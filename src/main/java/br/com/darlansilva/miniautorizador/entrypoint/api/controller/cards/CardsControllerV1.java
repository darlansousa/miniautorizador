package br.com.darlansilva.miniautorizador.entrypoint.api.controller.cards;

import java.math.BigDecimal;

import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.CardInputFormDto;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.output.CardOutputDto;

public interface CardsControllerV1 {

    BigDecimal findBalanceByCardNumber(String cardNumber);

    CardOutputDto save(CardInputFormDto input);

}

