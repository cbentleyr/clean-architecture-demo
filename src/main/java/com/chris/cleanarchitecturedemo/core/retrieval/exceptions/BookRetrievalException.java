package com.chris.cleanarchitecturedemo.core.retrieval.exceptions;

public class BookRetrievalException extends Exception {

    public BookRetrievalException(RuntimeException runtimeException) {
        initCause(runtimeException);
    }
}
