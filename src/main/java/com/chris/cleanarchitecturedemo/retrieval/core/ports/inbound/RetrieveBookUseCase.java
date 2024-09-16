package com.chris.cleanarchitecturedemo.retrieval.core.ports.inbound;

import com.chris.cleanarchitecturedemo.retrieval.core.domain.Book;
import com.chris.cleanarchitecturedemo.retrieval.core.exceptions.BookRetrievalException;
import com.chris.cleanarchitecturedemo.retrieval.core.exceptions.BookNotFoundException;

import java.util.UUID;

public interface RetrieveBookUseCase {

    Book retrieveBook(UUID id) throws BookNotFoundException, BookRetrievalException;
}
