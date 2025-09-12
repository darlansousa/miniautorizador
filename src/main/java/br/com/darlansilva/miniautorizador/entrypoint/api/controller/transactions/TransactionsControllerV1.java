package br.com.darlansilva.miniautorizador.entrypoint.api.controller.transactions;

import br.com.darlansilva.miniautorizador.core.domain.TransactionStatus;
import br.com.darlansilva.miniautorizador.entrypoint.api.dto.input.TransactionInputFormDto;

public interface TransactionsControllerV1 {

    TransactionStatus pay(TransactionInputFormDto input);

}

