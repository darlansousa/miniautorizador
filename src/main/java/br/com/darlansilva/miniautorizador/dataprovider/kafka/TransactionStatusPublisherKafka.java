package br.com.darlansilva.miniautorizador.dataprovider.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import br.com.darlansilva.miniautorizador.core.domain.TransactionStatusEvent;
import br.com.darlansilva.miniautorizador.core.gateway.TransactionStatusPublisherGateway;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransactionStatusPublisherKafka implements TransactionStatusPublisherGateway {

    public final KafkaTemplate<String, Object> template;

    @Override
    public void publish(TransactionStatusEvent event) {
        template.send("payment-status", event);
    }

}
