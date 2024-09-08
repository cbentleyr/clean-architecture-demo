package com.chris.cleanarchitecturedemo.bookIngestion.adapters.persistence;

import com.chris.cleanarchitecturedemo.bookIngestion.core.ports.out.BookPersistencePort;
import com.chris.cleanarchitecturedemo.common.adapters.persistence.BookEntity;
import com.chris.cleanarchitecturedemo.common.adapters.persistence.BookRepository;
import com.chris.cleanarchitecturedemo.common.core.entities.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;

    @Override
    public UUID saveBook(Book book) {
        var entity = new BookEntity(book);
        return bookRepository.save(entity).getId();
    }
}
