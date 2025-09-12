package br.com.darlansilva.miniautorizador.entrypoint.api.controller.cards.impl;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.darlansilva.miniautorizador.entrypoint.api.controller.cards.CardsControllerV1;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.CardInputFormDto;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.output.CardOutputDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cartoes")
@Tag(name = "Cartões", description = "Através deste recurso é possível gerenciar os cartões do sistema")
public class CardsControllerV1Impl implements CardsControllerV1 {

    @GetMapping("/{numeroCartao}")
    @Operation(
            summary = "Recuperar saldo do cartão",
            description = "Retorna saldo do cartão pelo número do cartão",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(schema = @Schema(implementation = BigDecimal.class))
                    }),
                    @ApiResponse(responseCode = "401", description = "Cartão não existente")
            }
    )
    @Override
    public BigDecimal findBalanceByCardNumber(@PathVariable(name = "numeroCartao") String cardNumber) {
        return BigDecimal.TEN;
    }

    @PostMapping
    @Operation(
            summary = "Criar cartão",
            description = "Cadastrar um cartão no sistema",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = CardOutputDto.class))
                    }),
                    @ApiResponse(responseCode = "424", description = "Violação de regras de negócio"),
                    @ApiResponse(responseCode = "422", description = "Cartão já existente")
            }
    )
    @Override
    public CardOutputDto save(@RequestBody @Valid CardInputFormDto input) {
        return CardOutputDto.builder()
                .senha("1234")
                .numeroCartao("6549873025634501")
                .build();
    }

}
