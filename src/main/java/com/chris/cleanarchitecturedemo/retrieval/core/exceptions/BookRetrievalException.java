package com.chris.cleanarchitecturedemo.retrieval.core.exceptions;

public class BookRetrievalException extends Exception {

    public BookRetrievalException(RuntimeException runtimeException) {
        initCause(runtimeException);
    }
}
