package br.com.darlansilva.miniautorizador.core.gateway;

import java.util.Optional;

import br.com.darlansilva.miniautorizador.core.domain.CardAccount;

public interface CardAccountGateway {

    void save(CardAccount cardAccount);
    Optional<CardAccount> findBy(String username, String cardNumber);

}
