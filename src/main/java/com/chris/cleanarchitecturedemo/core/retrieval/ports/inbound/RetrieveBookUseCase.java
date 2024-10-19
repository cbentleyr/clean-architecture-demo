package com.chris.cleanarchitecturedemo.core.retrieval.ports.inbound;

import com.chris.cleanarchitecturedemo.core.domain.Book;
import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookRetrievalException;
import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookNotFoundException;

import java.util.UUID;

public interface RetrieveBookUseCase {

    Book retrieveBook(UUID id) throws BookNotFoundException, BookRetrievalException;
}
