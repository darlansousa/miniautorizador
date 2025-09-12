package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.core.gateway.CardGateway;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.CardEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.CardJPARepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardRepository implements CardGateway {
    private final CardJPARepository repository;
    private final CardEntityMapper mapper;

    @Override
    public List<Card> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), true)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Card> findBy(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Card> findBy(String cardNumber) {
        return repository.findByCardNumber(cardNumber).map(mapper::toDomain);
    }

    @Override
    public Card save(Card card) {
        return mapper.toDomain(repository.save(mapper.toEntity(card)));
    }

    @Override
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
