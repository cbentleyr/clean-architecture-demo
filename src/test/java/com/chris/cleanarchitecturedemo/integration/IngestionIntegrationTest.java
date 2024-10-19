package com.chris.cleanarchitecturedemo.integration;

import com.chris.cleanarchitecturedemo.adapters.kafka.KafkaListenerAdapter;
import com.chris.cleanarchitecturedemo.adapters.postgres.BookEntity;
import com.chris.cleanarchitecturedemo.core.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;

import static com.chris.cleanarchitecturedemo.integration.utils.KafkaIntegrationTestHelper.*;
import static com.chris.cleanarchitecturedemo.integration.utils.PostgresIntegrationTestHelper.buildPostgresContainer;
import static com.chris.cleanarchitecturedemo.integration.utils.PostgresIntegrationTestHelper.getPersistedBookEntities;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngestionIntegrationTest {

    private static final ConfluentKafkaContainer kafka = buildKafkaContainer();

    private static final PostgreSQLContainer postgres = buildPostgresContainer();

    static {
        kafka.start();
        postgres.start();
    }

    @DynamicPropertySource
    static void registerContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/postgres", postgres.getFirstMappedPort()));
        registry.add("spring.kafka.bootstrap-servers", () -> "localhost:" + kafka.getFirstMappedPort());
    }

    @SpyBean
    private KafkaListenerAdapter kafkaListenerAdapter;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @AfterEach
    void clearDownPostgres() {
        jdbcAggregateTemplate.deleteAll(BookEntity.class);
    }

    @Test
    public void ingestionModule_IngestsAValidBook() {
        var book = Book.builder().title("Harry Potter").author("JK Rowling").build();
        produceValidBook(book, kafka.getBootstrapServers());
        awaitConsumption();

        var persistedBooks = getPersistedBookEntities(jdbcAggregateTemplate);
        assertThat(persistedBooks).hasSize(1);
        assertBookPersistedWithExpectedDetails(book, persistedBooks.getFirst());
    }

    @Test
    public void ingestionModule_DoesNotIngestAnInvalidBook() {
        produceMalformedMessage(kafka.getBootstrapServers());
        awaitDeserialisationFailure();

        var persistedBooks = getPersistedBookEntities(jdbcAggregateTemplate);
        assertThat(persistedBooks).isEmpty();
    }

    private void assertBookPersistedWithExpectedDetails(Book expectedBook, BookEntity persistedBookEntity) {
        assertThat(persistedBookEntity.title()).isEqualTo(expectedBook.title());
        assertThat(persistedBookEntity.author()).isEqualTo(expectedBook.author());
    }

    private void awaitConsumption() {
        Awaitility.await()
                .atMost(Duration.ofSeconds(5))
                .until(() -> {
                    try {
                        // Wait for async call, then brief static wait
                        verify(kafkaListenerAdapter).listen(any());
                        Thread.sleep(500);
                        return true;
                    } catch (AssertionError error) {
                        return false;
                    }
                });
    }

    private void awaitDeserialisationFailure() {
        // Static wait - better with a dynamic event to hook on to
        Awaitility.await()
                .atMost(Duration.ofSeconds(5));
    }
}