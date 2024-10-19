package com.chris.cleanarchitecturedemo.core.ingestion.exceptions;

public class TransientPersistenceException extends Exception {

    public TransientPersistenceException(RuntimeException runtimeException) {
        initCause(runtimeException);
    }
}
