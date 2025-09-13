package br.com.darlansilva.miniautorizador.core.usecase.user.dto;

import java.util.List;

import br.com.darlansilva.miniautorizador.core.common.UserRole;
import br.com.darlansilva.miniautorizador.core.domain.User;

public record UserInputDto(
        String username,
        String password,
        List<UserRole>roles
) {

    public User toDomainWithPassword(String encodedPassword) {
        return User.from(
                null,
                this.username,
                encodedPassword,
                this.roles
        );
    }
}