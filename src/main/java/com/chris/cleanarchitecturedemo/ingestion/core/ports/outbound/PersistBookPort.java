package com.chris.cleanarchitecturedemo.ingestion.core.ports.outbound;

import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.TransientPersistenceException;

import java.util.UUID;

public interface PersistBookPort {

    UUID saveBook(Book book) throws TransientPersistenceException, NonTransientPersistenceException;
}
