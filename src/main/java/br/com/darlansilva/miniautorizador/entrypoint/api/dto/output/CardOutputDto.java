package br.com.darlansilva.miniautorizador.entrypoint.api.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardOutputDto {
    private String senha;
    private String numeroCartao;
}
