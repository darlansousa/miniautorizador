package br.com.darlansilva.miniautorizador.core.exception;

public class CardNotFoundException extends UseCaseException{
    public CardNotFoundException() {
        super("CARTAO_INEXISTENTE");
    }
}
