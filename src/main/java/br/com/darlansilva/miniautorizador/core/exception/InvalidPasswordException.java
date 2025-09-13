package br.com.darlansilva.miniautorizador.core.exception;

import static br.com.darlansilva.miniautorizador.core.domain.TransactionStatus.INVALID_PASSWORD;

public class InvalidPasswordException extends NotAuthorizedTransaction{
    public InvalidPasswordException() {
        super(INVALID_PASSWORD.getValue());
    }
}
