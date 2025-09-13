package br.com.darlansilva.miniautorizador.entrypoint.api.dto.output;

import br.com.darlansilva.miniautorizador.core.domain.Card;
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

    public static CardOutputDto from(Card card) {
        return CardOutputDto.builder()
                .numeroCartao(card.getCardNumber())
                .senha(card.getPassword())
                .build();
    }
}
