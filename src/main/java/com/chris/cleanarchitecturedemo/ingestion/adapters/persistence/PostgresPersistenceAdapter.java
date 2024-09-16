package com.chris.cleanarchitecturedemo.ingestion.adapters.persistence;

import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.ports.outbound.PersistBookPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresPersistenceAdapter implements PersistBookPort {

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public UUID saveBook(Book book) throws TransientPersistenceException, NonTransientPersistenceException {
        var entity = new BookPersistenceEntity(book);
        try {
            return jdbcAggregateTemplate.insert(entity).id();
        } catch (CannotGetJdbcConnectionException exception) {
            throw new TransientPersistenceException(exception);
        } catch (RuntimeException exception) {
            throw new NonTransientPersistenceException(exception);
        }
    }
}
