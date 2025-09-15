package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.darlansilva.miniautorizador.core.domain.CardAccount;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardAccountEntity;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardEntity;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.UserEntity;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.CardAccountEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.CardAccountJPARepository;

@ExtendWith(MockitoExtension.class)
class CardAccountRepositoryTest {

    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private CardAccountRepository subject;

    @Mock
    private CardAccountJPARepository repositoryMock;

    @Mock
    private CardAccountEntityMapper mapperMock;


    @Test
    void shouldSaveCardAccount() {
        final var domain = EASY_RANDOM.nextObject(CardAccount.class);
        final var entity = CardAccountEntity.builder()
                .id(domain.getId())
                .card(CardEntity.builder()
                              .cardNumber(domain.getCard().getCardNumber())
                              .id(domain.getCard().getId())
                              .password(domain.getCard().getPassword())
                              .build())
                .user(UserEntity.builder()
                              .username(domain.getUser().getUsername())
                              .password(domain.getUser().getPassword())
                              .build())
                .build();

        given(repositoryMock.save(entity)).willReturn(entity);
        given(mapperMock.toEntity(domain)).willReturn(entity);

        subject.save(domain);

        then(repositoryMock).should().save(entity);
        then(mapperMock).should().toEntity(domain);
    }

    @Test
    void shouldFindByUsernameAndCardNumber() {
        final var domain = EASY_RANDOM.nextObject(CardAccount.class);
        final var username = domain.getUser().getUsername();
        final var cardNumber = domain.getCard().getCardNumber();
        final var entity = CardAccountEntity.builder()
                .id(domain.getId())
                .card(CardEntity.builder()
                              .cardNumber(cardNumber)
                              .id(domain.getCard().getId())
                              .password(domain.getCard().getPassword())
                              .build())
                .user(UserEntity.builder()
                              .username(username)
                              .password(cardNumber)
                              .build())
                .build();

        given(repositoryMock.findByUserUsernameAndCardCardNumber(
                username,
                cardNumber)
        ).willReturn(java.util.Optional.of(entity));

        given(mapperMock.toDomain(entity)).willReturn(domain);

        final var actual = subject.findBy(username, cardNumber);

        then(repositoryMock).should().findByUserUsernameAndCardCardNumber(username, cardNumber);
        then(mapperMock).should().toDomain(entity);

        assert actual.isPresent();
        assert actual.get().equals(domain);
    }

}