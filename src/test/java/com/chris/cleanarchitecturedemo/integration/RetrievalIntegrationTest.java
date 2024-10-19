package com.chris.cleanarchitecturedemo.integration;

import com.chris.cleanarchitecturedemo.adapters.postgres.BookEntity;
import com.chris.cleanarchitecturedemo.adapters.rest.GetBookResponse;
import com.chris.cleanarchitecturedemo.adapters.rest.RestRetrievalAdapter;
import com.chris.cleanarchitecturedemo.core.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RetrievalIntegrationTest {

    private static final PostgreSQLContainer postgres = new PostgreSQLContainer()
            .withDatabaseName("postgres")
            .withUsername("admin")
            .withPassword("password");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/postgres", postgres.getFirstMappedPort()));
    }

    @Autowired
    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @Autowired
    private RestRetrievalAdapter restRetrievalAdapter;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @AfterEach
    void clearDownPostgres() {
        jdbcAggregateTemplate.deleteAll(BookEntity.class);
    }

    @Test
    public void retrievalModule_ReturnsAnExistingBook() {
        var id = UUID.randomUUID();
        var book = Book.builder().id(id).title("Harry Potter").author("JK Rowling").build();
        persistBook(book);

        var response = testRestTemplate.getForEntity(
                "/book/" + id,
                GetBookResponse.class
        );
        var responseBody = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo(book.title());
        assertThat(responseBody.getAuthor()).isEqualTo(book.author());
    }

    @Test
    public void retrievalModule_ReturnsNotFoundIfNotExisting() {
        var randomId = UUID.randomUUID();

        var response = testRestTemplate.getForEntity(
                "/book/" + randomId,
                GetBookResponse.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void persistBook(Book book) {
        var bookEntity = new BookEntity(book);
        jdbcAggregateTemplate.insert(bookEntity);
    }
}