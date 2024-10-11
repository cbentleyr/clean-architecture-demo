package com.chris.cleanarchitecturedemo.retrieval;

import com.chris.cleanarchitecturedemo.ingestion.adapters.persistence.BookPersistenceEntity;
import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import com.chris.cleanarchitecturedemo.retrieval.adapters.rest.RestRetrievalAdapter;
import com.chris.cleanarchitecturedemo.retrieval.adapters.retrieval.BookRetrievalEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.modulith.test.ApplicationModuleTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ApplicationModuleTest
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

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(restRetrievalAdapter).build();
    }

    @AfterEach
    void clearDownPostgres() {
        jdbcAggregateTemplate.deleteAll(BookRetrievalEntity.class);
    }

    @Test
    public void retrievalModule_ReturnsAnExistingBook() throws Exception {
        var id = UUID.randomUUID();
        var book = Book.builder().id(id).title("Harry Potter").author("JK Rowling").build();
        persistBook(book);

        mockMvc.perform(
                get("/book/{id}", id)
        ).andExpectAll(
                status().isOk(),
                jsonPath("title").value(book.title()),
                jsonPath("author").value(book.author())
        );
    }

    @Test
    public void retrievalModule_ReturnsNotFoundIfNotExisting() throws Exception {
        var randomId = UUID.randomUUID();

        mockMvc.perform(
                get("/book/{id}", randomId)
        ).andExpect(
                status().isNotFound()
        );
    }

    private void persistBook(Book book) {
        var bookEntity = new BookPersistenceEntity(book);
        jdbcAggregateTemplate.insert(bookEntity);
    }
}