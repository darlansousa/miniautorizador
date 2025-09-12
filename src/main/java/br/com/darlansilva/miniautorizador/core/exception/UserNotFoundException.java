package br.com.darlansilva.miniautorizador.core.exception;

public class UserNotFoundException extends UseCaseException{
    public UserNotFoundException() {
        super("USER_NOT_FOUND");
    }
}
