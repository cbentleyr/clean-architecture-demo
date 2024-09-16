package com.chris.cleanarchitecturedemo.retrieval.adapters.retrieval;

import com.chris.cleanarchitecturedemo.retrieval.core.domain.Book;
import com.chris.cleanarchitecturedemo.retrieval.core.ports.outbound.RetrieveBookPort;
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
        Optional<BookRetrievalEntity> maybeEntity = bookRetrievalRepository.findById(id);
        if (maybeEntity.isPresent()) {
            return maybeEntity.map(BookRetrievalEntity::toBook);
        }
        return Optional.empty();
    }
}
