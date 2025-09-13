package br.com.darlansilva.miniautorizador.core.exception;

public class NotAuthorizedTransaction extends UseCaseException{
    public NotAuthorizedTransaction(String code) {
        super(code);
    }
}
