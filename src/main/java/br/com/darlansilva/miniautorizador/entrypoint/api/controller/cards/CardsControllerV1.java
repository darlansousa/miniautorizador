package br.com.darlansilva.miniautorizador.entrypoint.api.controller.cards;

import java.math.BigDecimal;
import java.security.Principal;

import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.CardInputFormDto;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.output.CardOutputDto;

public interface CardsControllerV1 {

    BigDecimal findBalanceByCardNumber(String cardNumber, Principal principal);

    CardOutputDto save(CardInputFormDto input, Principal principal);

}

