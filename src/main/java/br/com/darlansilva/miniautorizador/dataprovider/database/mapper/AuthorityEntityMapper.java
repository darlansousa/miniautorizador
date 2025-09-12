package br.com.darlansilva.miniautorizador.dataprovider.database.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import br.com.darlansilva.miniautorizador.core.common.BaseMapper;
import br.com.darlansilva.miniautorizador.core.domain.Authority;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.AuthorityEntity;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {UserEntityMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorityEntityMapper extends BaseMapper<AuthorityEntity, Authority> {
}
