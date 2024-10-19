package com.chris.cleanarchitecturedemo.core.ingestion.ports.outbound;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.TransientPersistenceException;

import java.util.UUID;

public interface PersistBookPort {

    UUID saveBook(Book book) throws TransientPersistenceException, NonTransientPersistenceException;
}
