package com.chris.cleanarchitecturedemo.ingestion.core.exceptions;

public class TransientPersistenceException extends Exception {

    public TransientPersistenceException(RuntimeException runtimeException) {
        initCause(runtimeException);
    }
}
