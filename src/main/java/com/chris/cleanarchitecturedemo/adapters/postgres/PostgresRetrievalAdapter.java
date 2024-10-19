package com.chris.cleanarchitecturedemo.adapters.postgres;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.outbound.RetrieveBookPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresRetrievalAdapter implements RetrieveBookPort {

    private final BookRetrievalRepository bookRetrievalRepository;

    @Override
    public Optional<Book> getById(UUID id) {
        Optional<BookEntity> maybeEntity = bookRetrievalRepository.findById(id);
        if (maybeEntity.isPresent()) {
            return maybeEntity.map(BookEntity::toBook);
        }
        return Optional.empty();
    }
}
