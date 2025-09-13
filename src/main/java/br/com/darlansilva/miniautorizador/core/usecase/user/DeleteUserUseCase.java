package br.com.darlansilva.miniautorizador.core.usecase.user;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import br.com.darlansilva.miniautorizador.core.exception.UserNotFoundException;
import br.com.darlansilva.miniautorizador.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UserGateway usersGateway;

    @Transactional
    public void deleteBy(Long id) {
        final var user = usersGateway.findBy(id).orElseThrow(UserNotFoundException::new);
        usersGateway.deleteBy(user.getId());
    }
}
