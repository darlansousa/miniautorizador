package br.com.darlansilva.miniautorizador.core.exception;

public class InvalidPasswordException extends UseCaseException{
    public InvalidPasswordException() {
        super("SENHA_INVALIDA");
    }
}
