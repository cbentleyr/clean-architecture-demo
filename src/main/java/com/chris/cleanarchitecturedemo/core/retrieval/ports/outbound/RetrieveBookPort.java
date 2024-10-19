package com.chris.cleanarchitecturedemo.core.retrieval.ports.outbound;

import com.chris.cleanarchitecturedemo.core.domain.Book;

import java.util.Optional;
import java.util.UUID;

public interface RetrieveBookPort {

    Optional<Book> getById(UUID id);
}
