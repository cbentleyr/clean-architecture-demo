package com.chris.cleanarchitecturedemo.core.retrieval;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookRetrievalException;
import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookNotFoundException;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.inbound.RetrieveBookUseCase;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.outbound.RetrieveBookPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class RetrieveBookService implements RetrieveBookUseCase {

    private final RetrieveBookPort retrieveBookPort;

    @Override
    public Book retrieveBook(UUID id) throws BookNotFoundException, BookRetrievalException {
        Optional<Book> maybeBook = attemptRetrievalById(id);
        if (maybeBook.isEmpty()) {
            log.error(String.format("Book not found for id: %s.", id));
            throw new BookNotFoundException();
        }
        log.info("Book retrieved for id: {}.", id);
        return maybeBook.get();
    }

    private Optional<Book> attemptRetrievalById(UUID id) throws BookRetrievalException {
        try {
            return retrieveBookPort.getById(id);
        } catch (RuntimeException e) {
            log.error(String.format("Unable to retrieve book for id: %s.", id), e);
            throw new BookRetrievalException(e);
        }
    }
}
