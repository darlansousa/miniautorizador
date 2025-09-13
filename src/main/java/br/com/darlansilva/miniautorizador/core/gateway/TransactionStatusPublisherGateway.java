package br.com.darlansilva.miniautorizador.core.gateway;

import br.com.darlansilva.miniautorizador.core.domain.TransactionStatusEvent;

public interface TransactionStatusPublisherGateway {

    void publish(TransactionStatusEvent event);
}
