package com.chris.cleanarchitecturedemo.ingestion.adapters.messaging;

public record BookMessage (
        String title,
        String author
) {}
