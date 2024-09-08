package com.chris.cleanarchitecturedemo.common.adapters.persistence;

import com.chris.cleanarchitecturedemo.common.core.entities.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity(name = "book")
@NoArgsConstructor
public class BookEntity {
    @Id
    private UUID id;
    private String title;
    private String author;

    public BookEntity(Book book) {
        id = book.getId();
        title = book.getTitle();
        author = book.getAuthor();
    }

    public Book toBook() {
        return Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .build();
    }
}
