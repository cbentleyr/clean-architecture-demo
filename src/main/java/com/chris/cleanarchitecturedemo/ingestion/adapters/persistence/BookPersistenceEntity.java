package com.chris.cleanarchitecturedemo.ingestion.adapters.persistence;

import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("book")
public record BookPersistenceEntity (
        @Id
        UUID id,
        String title,
        String author
) {
        public BookPersistenceEntity(Book book) {
                this(book.id(), book.title(), book.author());
        }
}
