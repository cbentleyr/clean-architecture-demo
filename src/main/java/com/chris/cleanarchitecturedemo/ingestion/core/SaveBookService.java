package com.chris.cleanarchitecturedemo.ingestion.core;

import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.ports.outbound.PersistBookPort;
import com.chris.cleanarchitecturedemo.ingestion.core.ports.inbound.SaveBookUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SaveBookService implements SaveBookUseCase {

    private final PersistBookPort persistBookPort;

    @Override
    public void saveBook(SaveBookCommand command) throws TransientPersistenceException, NonTransientPersistenceException {
        log.info("Attempting to persist a book with title: {}, and author: {}.",
                command.title(), command.author());
        var book = Book.builder()
                .id(UUID.randomUUID())
                .title(command.title())
                .author(command.author())
                .build();
        UUID savedId = persistBookPort.saveBook(book);
        log.info("Successfully persisted book with id: {}.", savedId);
    }
}
