package com.chris.cleanarchitecturedemo.bookIngestion.core.ports.out;

import com.chris.cleanarchitecturedemo.common.core.entities.Book;

import java.util.UUID;

public interface BookPersistencePort {

    UUID saveBook(Book book);
}
