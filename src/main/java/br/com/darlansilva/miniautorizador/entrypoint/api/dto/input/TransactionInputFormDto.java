package br.com.darlansilva.miniautorizador.entrypoint.api.dto.input;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInputFormDto {
    @NotNull
    private String numeroCartao;
    @NotNull
    private String senhaCartao;
    @NotNull
    private BigDecimal valor;
}
