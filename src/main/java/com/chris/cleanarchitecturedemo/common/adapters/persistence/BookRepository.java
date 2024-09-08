package com.chris.cleanarchitecturedemo.common.adapters.persistence;

import com.chris.cleanarchitecturedemo.common.adapters.persistence.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {
}
