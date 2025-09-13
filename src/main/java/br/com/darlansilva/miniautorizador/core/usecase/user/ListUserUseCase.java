package br.com.darlansilva.miniautorizador.core.usecase.user;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.darlansilva.miniautorizador.core.domain.User;
import br.com.darlansilva.miniautorizador.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ListUserUseCase {
    
    private final UserGateway usersGateway;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usersGateway.findAll();
    }

}
