package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.User;
import br.com.darlansilva.miniautorizador.core.gateway.UserGateway;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.UserEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepository implements UserGateway {

    private final UserJPARepository repository;
    private final UserEntityMapper mapper;

    @Override
    public List<User> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), true)
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findBy(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findBy(String username) {
        return repository.findByUsername(username).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }

    @Override
    public void deleteBy(Long id) {
        repository.deleteById(id);
    }
}
