package br.com.darlansilva.miniautorizador.dataprovider.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardAccountEntity;

@Repository
public interface CardAccountJPARepository extends CrudRepository<CardAccountEntity, Long> {

    Optional<CardAccountEntity> findByUserUsernameAndCardCardNumber(String username, String cardNumber);
}