package com.chris.cleanarchitecturedemo.core.ingestion.exceptions;


public class NonTransientPersistenceException extends Exception {

  public NonTransientPersistenceException(RuntimeException runtimeException) {
    initCause(runtimeException);
  }
}
