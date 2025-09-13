package br.com.darlansilva.miniautorizador.core.domain;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStatusEvent {
    private UUID transactionId;
    private String username;
    private TransactionStatus status;
    private BigDecimal amount;
}

