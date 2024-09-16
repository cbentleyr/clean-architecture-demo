package com.chris.cleanarchitecturedemo.ingestion.core.exceptions;


public class NonTransientPersistenceException extends Exception {

  public NonTransientPersistenceException(RuntimeException runtimeException) {
    initCause(runtimeException);
  }
}
