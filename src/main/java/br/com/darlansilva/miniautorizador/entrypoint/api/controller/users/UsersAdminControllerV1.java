package br.com.darlansilva.miniautorizador.entrypoint.api.controller.users;

import java.util.List;

import br.com.darlansilva.miniautorizador.core.domain.User;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.UserInputFormDto;

public interface UsersAdminControllerV1 {

    List<User> getAll();

    User save(UserInputFormDto input);

    void delete(Long id);
}

