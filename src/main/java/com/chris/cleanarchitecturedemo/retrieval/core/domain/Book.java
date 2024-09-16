package com.chris.cleanarchitecturedemo.retrieval.core.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Book (
        UUID id,
        String title,
        String author
) {}
