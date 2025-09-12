package br.com.darlansilva.miniautorizador.core.gateway;

import java.util.List;
import java.util.Optional;

import br.com.darlansilva.miniautorizador.core.domain.Card;

public interface CardGateway {

    List<Card> findAll();

    Optional<Card> findBy(Long id);

    Optional<Card> findBy(String cardNumber);

    Card save(Card card);

    void deleteBy(Long id);

}
