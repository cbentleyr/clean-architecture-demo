package com.chris.cleanarchitecturedemo.ingestion.core.ports.inbound;

import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.NonTransientPersistenceException;
import lombok.Builder;

public interface SaveBookUseCase {

    void saveBook(SaveBookCommand command) throws TransientPersistenceException, NonTransientPersistenceException;

    @Builder
    record SaveBookCommand (String title, String author) {}
}
