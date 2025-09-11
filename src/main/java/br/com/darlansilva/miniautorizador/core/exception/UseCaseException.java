package br.com.darlansilva.miniautorizador.core.exception;

public class UseCaseException extends RuntimeException {

    public UseCaseException(String code, Throwable cause) {
        super(code, cause);
    }

    public UseCaseException(String code) {
        super(code);
    }

}
