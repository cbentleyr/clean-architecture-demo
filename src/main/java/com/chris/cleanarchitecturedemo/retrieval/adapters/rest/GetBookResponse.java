package com.chris.cleanarchitecturedemo.retrieval.adapters.rest;

import com.chris.cleanarchitecturedemo.retrieval.core.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class GetBookResponse {

    private final String title;
    private final String author;

    public GetBookResponse(Book book) {
        title = book.title();
        author = book.author();
    }
}
