package br.com.darlansilva.miniautorizador.dataprovider.database.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import br.com.darlansilva.miniautorizador.core.common.BaseMapper;
import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardAccountEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {CardEntityMapper.class, UserEntityMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CardAccountEntityMapper extends BaseMapper<CardAccountEntity, CardAccount> {

    @Mapping(target = "card", ignore = true)
    CardAccount toDomain(CardAccountEntity entity);
}
