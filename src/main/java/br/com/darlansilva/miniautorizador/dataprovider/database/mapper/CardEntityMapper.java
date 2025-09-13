package br.com.darlansilva.miniautorizador.dataprovider.database.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.com.darlansilva.miniautorizador.core.common.BaseMapper;
import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CardEntityMapper extends BaseMapper<CardEntity, Card> {

}
