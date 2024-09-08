package com.chris.cleanarchitecturedemo.bookIngestion.core.ports.in;

import lombok.Builder;

public interface SaveBookUseCase {

    void saveBook(SaveBookCommand command);

    @Builder
    record SaveBookCommand (String title, String author) {}
}
