package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.Authority;
import br.com.darlansilva.miniautorizador.core.gateway.AuthorityGateway;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.AuthorityEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.AuthorityJPARepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorityRepository implements AuthorityGateway {

    private final AuthorityJPARepository repository;
    private final AuthorityEntityMapper mapper;

    @Override
    public void register(Authority authority) {
        repository.save(mapper.toEntity(authority));
    }
}
