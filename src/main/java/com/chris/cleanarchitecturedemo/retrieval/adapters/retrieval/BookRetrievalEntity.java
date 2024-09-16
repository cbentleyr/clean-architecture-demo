package com.chris.cleanarchitecturedemo.retrieval.adapters.retrieval;

import com.chris.cleanarchitecturedemo.retrieval.core.domain.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("book")
public record BookRetrievalEntity (
        @Id
        UUID id,
        String title,
        String author
) {

    public Book toBook() {
        return Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
    }
}
