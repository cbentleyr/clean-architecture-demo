package com.chris.cleanarchitecturedemo.adapters.kafka;

public record BookMessage (
        String title,
        String author
) {}
