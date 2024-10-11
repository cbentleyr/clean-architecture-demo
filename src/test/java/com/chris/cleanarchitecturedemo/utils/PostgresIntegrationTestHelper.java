package com.chris.cleanarchitecturedemo.utils;

import com.chris.cleanarchitecturedemo.ingestion.adapters.persistence.BookPersistenceEntity;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.ArrayList;
import java.util.List;

public class PostgresIntegrationTestHelper {

    public static PostgreSQLContainer buildPostgresContainer() {
        return new PostgreSQLContainer()
                .withDatabaseName("postgres")
                .withUsername("admin")
                .withPassword("password");
    }

    public static List<BookPersistenceEntity> getPersistedBookEntities(JdbcAggregateTemplate jdbcAggregateTemplate) {
        var entities = new ArrayList<BookPersistenceEntity>();
        jdbcAggregateTemplate.findAll(BookPersistenceEntity.class).forEach(entities::add);
        return entities;
    }
}
