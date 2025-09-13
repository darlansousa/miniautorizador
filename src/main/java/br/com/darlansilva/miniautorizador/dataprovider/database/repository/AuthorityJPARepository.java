package br.com.darlansilva.miniautorizador.dataprovider.database.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.darlansilva.miniautorizador.dataprovider.database.entity.AuthorityEntity;

@Repository
public interface AuthorityJPARepository extends CrudRepository<AuthorityEntity, Long> {

}