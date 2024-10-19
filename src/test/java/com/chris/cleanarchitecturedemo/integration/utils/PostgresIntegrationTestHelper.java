package com.chris.cleanarchitecturedemo.integration.utils;

import com.chris.cleanarchitecturedemo.adapters.postgres.BookEntity;
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

    public static List<BookEntity> getPersistedBookEntities(JdbcAggregateTemplate jdbcAggregateTemplate) {
        var entities = new ArrayList<BookEntity>();
        jdbcAggregateTemplate.findAll(BookEntity.class).forEach(entities::add);
        return entities;
    }
}
