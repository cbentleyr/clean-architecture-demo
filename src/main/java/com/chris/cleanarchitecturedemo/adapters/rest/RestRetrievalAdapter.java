package com.chris.cleanarchitecturedemo.adapters.rest;

import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookRetrievalException;
import com.chris.cleanarchitecturedemo.core.retrieval.exceptions.BookNotFoundException;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.inbound.RetrieveBookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RestRetrievalAdapter {

    private final RetrieveBookUseCase retrieveBookUseCase;

    @GetMapping("/book/{id}")
    public ResponseEntity<GetBookResponse> getBookById(@NonNull @PathVariable UUID id) {
        try {
            var book = retrieveBookUseCase.retrieveBook(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GetBookResponse(book));
        } catch (BookRetrievalException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
