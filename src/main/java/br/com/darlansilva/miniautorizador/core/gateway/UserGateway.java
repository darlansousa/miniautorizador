package br.com.darlansilva.miniautorizador.core.gateway;

import java.util.List;
import java.util.Optional;

import br.com.darlansilva.miniautorizador.core.domain.User;

public interface UserGateway {

    List<User> findAll();

    Optional<User> findBy(Long id);

    Optional<User> findBy(String username);

    User save(User user);

    void deleteBy(Long id);

}
