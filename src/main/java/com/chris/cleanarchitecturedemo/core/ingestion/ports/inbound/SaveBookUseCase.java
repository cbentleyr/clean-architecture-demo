package com.chris.cleanarchitecturedemo.core.ingestion.ports.inbound;

import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.NonTransientPersistenceException;
import lombok.Builder;

public interface SaveBookUseCase {

    void saveBook(SaveBookCommand command) throws TransientPersistenceException, NonTransientPersistenceException;

    @Builder
    record SaveBookCommand (String title, String author) {}
}
