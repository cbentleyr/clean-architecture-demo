package com.chris.cleanarchitecturedemo.adapters.postgres;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.core.ingestion.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.core.ingestion.ports.outbound.PersistBookPort;
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
        var entity = new BookEntity(book);
        try {
            return jdbcAggregateTemplate.insert(entity).id();
        } catch (CannotGetJdbcConnectionException exception) {
            throw new TransientPersistenceException(exception);
        } catch (RuntimeException exception) {
            throw new NonTransientPersistenceException(exception);
        }
    }
}
