package br.com.darlansilva.miniautorizador.entrypoint.api.controller.users.impl;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.darlansilva.miniautorizador.core.domain.User;
import br.com.darlansilva.miniautorizador.entrypoint.api.controller.users.UsersAdminControllerV1;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.UserInputFormDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
@Tag(name = "Admin users V1", description = "Através deste recurso é possível gerenciar os usuários do sistema")
public class UsersAdminControllerV1Impl implements UsersAdminControllerV1 {


    @GetMapping
    @Operation(
            summary = "Recuperar usuários",
            description = "Retorna lista de usuários cadastradas",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = User.class, type = "array"))
                    })
            }
    )
    @Override
    public List<User> getAll() {
        return List.of(User.from(1L, "user", "password", List.of()));
    }

    @PostMapping
    @Operation(
            summary = "Criar usuário",
            description = "Cadastrar um usuário no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = User.class))
                    }),
                    @ApiResponse(responseCode = "424", description = "Violação de regras de negócio")
            }
    )
    @Override
    public User save(@RequestBody @Valid UserInputFormDto input) {
        return User.from(1L, "user", "password", List.of());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover usuário por ID",
            description = "Remover um usuário por ID",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "424", description = "Violação de regras de negócio")
            }
    )
    @Override
    public void delete(@PathVariable Long id) {

    }
}
