package br.com.darlansilva.miniautorizador.core.domain;

public enum TransactionStatus {

    OK("OK"),
    INSUFFICIENT_FUNDS("SALDO_INSUFICIENTE"),
    INVALID_PASSWORD("SENHA_INVALIDA"),
    CARD_NOT_FOUND("CARTAO_INEXISTENTE");

    private final String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
