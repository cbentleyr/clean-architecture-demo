package com.chris.cleanarchitecturedemo.integration;

import com.chris.cleanarchitecturedemo.adapters.kafka.KafkaListenerAdapter;
import com.chris.cleanarchitecturedemo.adapters.postgres.BookEntity;
import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.adapters.rest.GetBookResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.UUID;

import static com.chris.cleanarchitecturedemo.integration.utils.KafkaIntegrationTestHelper.buildKafkaContainer;
import static com.chris.cleanarchitecturedemo.integration.utils.KafkaIntegrationTestHelper.produceValidBook;
import static com.chris.cleanarchitecturedemo.integration.utils.PostgresIntegrationTestHelper.buildPostgresContainer;
import static com.chris.cleanarchitecturedemo.integration.utils.PostgresIntegrationTestHelper.getPersistedBookEntities;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class E2EIntegrationTest {

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

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @SpyBean
    private KafkaListenerAdapter kafkaListenerAdapter;

    @AfterEach
    void clearDownPostgres() {
        jdbcAggregateTemplate.deleteAll(BookEntity.class);
    }

    @Test
    void e2e_IngestAndRetrieveAValidBook() {
        var book = Book.builder()
                .title("A Game of Thrones")
                .author("GRR Martin")
                .build();

        produceValidBook(book, kafka.getBootstrapServers());
        awaitConsumption();
        var ingestedBookId = retrieveIngestedBookId();

        var response = testRestTemplate.getForEntity(
                "/book/" + ingestedBookId,
                GetBookResponse.class
                );
        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(book.title());
        assertThat(responseBody.getAuthor()).isEqualTo(book.author());
    }

    private UUID retrieveIngestedBookId() {
        var entities = getPersistedBookEntities(jdbcAggregateTemplate);
        assertThat(entities).hasSize(1);
        return entities.getFirst().id();
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
}
