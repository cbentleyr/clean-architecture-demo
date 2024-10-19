package com.chris.cleanarchitecturedemo.adapters.postgres;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("book")
public record BookEntity(
        @Id
        UUID id,
        String title,
        String author
) {
        public BookEntity(Book book) {
                this(book.id(), book.title(), book.author());
        }

        public Book toBook() {
                return Book.builder()
                        .id(id)
                        .title(title)
                        .author(author)
                        .build();
        }
}
