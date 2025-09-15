package br.com.darlansilva.miniautorizador.core.exception;

public class NotAuthenticatedException extends InfraException{
    public NotAuthenticatedException() {
        super("NOT_AUTHENTICATED");
    }
}
