package br.com.darlansilva.miniautorizador.entrypoint.api.dto.input;

import java.util.List;

import br.com.darlansilva.miniautorizador.core.common.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInputFormDto {

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private List<UserRole> roles;
}
