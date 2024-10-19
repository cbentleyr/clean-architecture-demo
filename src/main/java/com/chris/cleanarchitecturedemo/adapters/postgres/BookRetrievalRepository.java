package com.chris.cleanarchitecturedemo.adapters.postgres;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRetrievalRepository extends CrudRepository<BookEntity, UUID> {
}
