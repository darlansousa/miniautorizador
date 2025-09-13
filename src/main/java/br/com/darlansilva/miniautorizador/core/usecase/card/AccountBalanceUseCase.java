package br.com.darlansilva.miniautorizador.core.usecase.card;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.darlansilva.miniautorizador.core.exception.CardNotFoundException;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccountBalanceUseCase {

    private final CardAccountGateway cardAccountGateway;

    @Transactional(readOnly = true)
    public BigDecimal getBalanceBy(String cardNumber, String username) {
        return cardAccountGateway
                .findBy(username, cardNumber)
                .orElseThrow(CardNotFoundException::new)
                .getBalance();
    }
}
