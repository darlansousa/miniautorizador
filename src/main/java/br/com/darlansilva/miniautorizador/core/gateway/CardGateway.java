package br.com.darlansilva.miniautorizador.core.gateway;

import java.util.List;
import java.util.Optional;

import br.com.darlansilva.miniautorizador.core.domain.Card;

public interface CardGateway {

    List<Card> findAll();

    Optional<Card> findBy(Long id);

    Optional<Card> findBy(String cardNumber);

    Optional<Card> findBy(String cardNumber, String username);

    Optional<Card> findByCardNumberAndPassword(String cardNumber, String password);

    Card save(Card card);

    void deleteBy(Long id);

}
