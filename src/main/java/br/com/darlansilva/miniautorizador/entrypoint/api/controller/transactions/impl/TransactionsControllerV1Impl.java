package br.com.darlansilva.miniautorizador.entrypoint.api.controller.transactions.impl;

import java.util.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.darlansilva.miniautorizador.core.domain.TransactionStatus;
import br.com.darlansilva.miniautorizador.entrypoint.api.controller.transactions.TransactionsControllerV1;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.TransactionInputFormDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/transacoes")
@Tag(name = "Transações", description = "Através deste recurso é possível fazer transações")
public class TransactionsControllerV1Impl implements TransactionsControllerV1 {

    @PostMapping
    @Operation(
            summary = "Fazer pagamento",
            description = "Fazer uma transação de pagamento",
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(schema = @Schema(implementation = TransactionStatus.class))
                    }),
                    @ApiResponse(responseCode = "422", description = "Erro na transação")
            }
    )
    @Override
    public TransactionStatus pay(@RequestBody @Valid TransactionInputFormDto input) {
        return Arrays.stream(TransactionStatus.values()).findAny().orElseThrow();
    }
}
