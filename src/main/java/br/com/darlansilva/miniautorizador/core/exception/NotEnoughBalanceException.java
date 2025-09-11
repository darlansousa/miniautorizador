package br.com.darlansilva.miniautorizador.core.exception;

public class NotEnoughBalanceException extends UseCaseException{
    public NotEnoughBalanceException() {
        super("SALDO_INSUFICIENTE");
    }
}
