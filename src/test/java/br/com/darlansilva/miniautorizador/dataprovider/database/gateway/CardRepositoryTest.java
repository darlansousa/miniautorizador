package br.com.darlansilva.miniautorizador.dataprovider.database.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.Optional;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.darlansilva.miniautorizador.core.domain.Card;
import br.com.darlansilva.miniautorizador.dataprovider.database.entity.CardEntity;
import br.com.darlansilva.miniautorizador.dataprovider.database.mapper.CardEntityMapper;
import br.com.darlansilva.miniautorizador.dataprovider.database.repository.CardJPARepository;


@ExtendWith(MockitoExtension.class)
class CardRepositoryTest {
    
    private static final EasyRandom EASY_RANDOM = new EasyRandom();

    @InjectMocks
    private CardRepository subject;
    
    @Mock
    private CardJPARepository repositoryMock;
    
    @Mock
    private CardEntityMapper mapperMock;


    @Test
    void shouldFindAllCards() {
        final var domain = EASY_RANDOM.nextObject(Card.class);
        final var card = CardEntity.builder()
                .id(domain.getId())
                .cardNumber(domain.getCardNumber())
                .password(domain.getPassword())
                .build();
        final var expected = List.of(domain);

        given(repositoryMock.findAll()).willReturn(List.of(card));
        given(mapperMock.toDomain(card)).willReturn(domain);

        assertEquals(expected, subject.findAll());

        then(repositoryMock).should().findAll();
        then(mapperMock).should().toDomain(card);
    }


    @Test
    void shouldFindCardById() {
        final var domain = EASY_RANDOM.nextObject(Card.class);
        final var card = CardEntity.builder()
                .id(domain.getId())
                .cardNumber(domain.getCardNumber())
                .password(domain.getPassword())
                .build();
        final var expected = Optional.of(domain);

        given(repositoryMock.findById(card.getId())).willReturn(Optional.of(card));
        given(mapperMock.toDomain(card)).willReturn(domain);

        assertEquals(expected, subject.findBy(card.getId()));

        then(repositoryMock).should().findById(card.getId());
        then(mapperMock).should().toDomain(card);
    }

    @Test
    void shouldFindCardByNumber() {
        final var domain = EASY_RANDOM.nextObject(Card.class);
        final var card = CardEntity.builder()
                .id(domain.getId())
                .cardNumber(domain.getCardNumber())
                .password(domain.getPassword())
                .build();
        final var expected = Optional.of(domain);

        given(repositoryMock.findByCardNumber(card.getCardNumber()))
                .willReturn(Optional.of(card));
        given(mapperMock.toDomain(card)).willReturn(domain);

        assertEquals(expected, subject.findBy(card.getCardNumber()));

        then(repositoryMock).should().findByCardNumber(card.getCardNumber());
        then(mapperMock).should().toDomain(card);
    }


    @Test
    void shouldReturnsEmptyWhenCardNotExists() {
        final var id = EASY_RANDOM.nextLong();
        final var expected = Optional.empty();

        given(repositoryMock.findById(id)).willReturn(Optional.empty());

        assertEquals(expected, subject.findBy(id));

        then(repositoryMock).should().findById(id);
        then(mapperMock).shouldHaveNoInteractions();
    }

    @Test
    void shouldReturnsEmptyWhenCardNotExistsOrPasswordIsInvalid() {
        final var cardNumber = EASY_RANDOM.nextObject(String.class);
        final var expected = Optional.empty();

        given(repositoryMock.findByCardNumber(cardNumber)).willReturn(Optional.empty());

        assertEquals(expected, subject.findBy(cardNumber));

        then(repositoryMock).should().findByCardNumber(cardNumber);
        then(mapperMock).shouldHaveNoInteractions();
    }

    @Test
    void shouldSaveCard() {
        final var domain = EASY_RANDOM.nextObject(Card.class);
        final var card = CardEntity.builder()
                .id(domain.getId())
                .cardNumber(domain.getCardNumber())
                .password(domain.getPassword())
                .build();

        given(repositoryMock.save(card)).willReturn(card);
        given(mapperMock.toDomain(card)).willReturn(domain);
        given(mapperMock.toEntity(domain)).willReturn(card);

        assertEquals(domain, subject.save(domain));

        then(repositoryMock).should().save(card);
        then(mapperMock).should().toDomain(card);
        then(mapperMock).should().toEntity(domain);
    }

    @Test
    void shouldDelete() {
        final var id = EASY_RANDOM.nextLong();
        subject.deleteBy(id);
        then(repositoryMock).should().deleteById(id);
        then(mapperMock).shouldHaveNoInteractions();
    }
    
}