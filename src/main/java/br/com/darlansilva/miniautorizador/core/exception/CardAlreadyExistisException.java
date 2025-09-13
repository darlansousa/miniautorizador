package br.com.darlansilva.miniautorizador.core.exception;

public class CardAlreadyExistisException extends UseCaseException{

    private final String cardNumber;
    private final String password;

    public CardAlreadyExistisException(String cardNumber, String password) {
        super("CARTAO_JA_EXISTE");
        this.cardNumber = cardNumber;
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPassword() {
        return password;
    }
}
