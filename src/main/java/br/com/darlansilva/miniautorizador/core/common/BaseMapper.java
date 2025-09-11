package br.com.darlansilva.miniautorizador.core.common;

public interface BaseMapper<E, D> {

    D toDomain(E entity);

    E toEntity(D domain);

}
