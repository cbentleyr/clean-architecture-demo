package com.chris.cleanarchitecturedemo.adapters.rest;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

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
