package br.com.darlansilva.miniautorizador.core.exception;

public class CardAlreadyExistisException extends UseCaseException{
    public CardAlreadyExistisException() {
        super("CARTAO_JA_EXISTE");
    }
}
