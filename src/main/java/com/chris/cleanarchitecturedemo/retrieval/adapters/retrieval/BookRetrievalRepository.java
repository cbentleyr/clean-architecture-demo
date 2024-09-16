package com.chris.cleanarchitecturedemo.retrieval.adapters.retrieval;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRetrievalRepository extends CrudRepository<BookRetrievalEntity, UUID> {
}
