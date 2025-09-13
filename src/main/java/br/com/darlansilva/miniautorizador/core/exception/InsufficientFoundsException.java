package br.com.darlansilva.miniautorizador.core.exception;

import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.INSUFFICIENT_FUNDS;

public class InsufficientFoundsException extends NotAuthorizedTransaction{
    public InsufficientFoundsException() {
        super(INSUFFICIENT_FUNDS.getValue());
    }
}
