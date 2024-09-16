package com.chris.cleanarchitecturedemo.retrieval.core.ports.outbound;

import com.chris.cleanarchitecturedemo.retrieval.core.domain.Book;

import java.util.Optional;
import java.util.UUID;

public interface RetrieveBookPort {

    Optional<Book> getById(UUID id);
}
