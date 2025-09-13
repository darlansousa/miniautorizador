package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.core.gateway.CardAccountGateway;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.CardAccountEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.CardAccountJPARepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CardAccountRepository implements CardAccountGateway {

    private final CardAccountJPARepository repository;
    private final CardAccountEntityMapper mapper;

    @Override
    public void save(CardAccount cardAccount) {
        repository.save(mapper.toEntity(cardAccount));
    }

    @Override
    public Optional<CardAccount> findBy(String username, String cardNumber) {
        return repository.findByUserUsernameAndCardCardNumber(username, cardNumber).map(mapper::toDomain);
    }
}
