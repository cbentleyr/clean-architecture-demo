package com.chris.cleanarchitecturedemo.bookIngestion.core.services;

import com.chris.cleanarchitecturedemo.bookIngestion.core.ports.out.BookPersistencePort;
import com.chris.cleanarchitecturedemo.bookIngestion.core.ports.in.SaveBookUseCase;
import com.chris.cleanarchitecturedemo.common.core.entities.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SaveBookService implements SaveBookUseCase {

    private final BookPersistencePort bookPersistencePort;

    @Override
    public void saveBook(SaveBookCommand command) {
        var book = Book.builder()
                .id(UUID.randomUUID())
                .title(command.title())
                .author(command.author())
                .build();
        UUID savedId = bookPersistencePort.saveBook(book);
        log.info("Saved book with id: {}", savedId);
    }
}
