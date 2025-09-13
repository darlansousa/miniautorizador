package br.com.darlansilva.miniautorizador.dataprovider.database.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardEntity;

@Repository
public interface CardJPARepository extends CrudRepository<CardEntity, Long> {

    Optional<CardEntity> findByCardNumber(String cardNumber);

    Optional<CardEntity> findByCardNumberAndCardAccountUserUsername(String cardNumber, String username);

    Optional<CardEntity> findByCardNumberAndPassword(String cardNumber, String password);



}