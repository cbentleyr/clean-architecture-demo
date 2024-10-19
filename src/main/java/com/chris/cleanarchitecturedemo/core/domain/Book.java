package com.chris.cleanarchitecturedemo.core.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Book (
        UUID id,
        String title,
        String author
) {}
