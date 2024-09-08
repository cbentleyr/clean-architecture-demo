package com.chris.cleanarchitecturedemo.common.core.entities;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Book {
    private UUID id;
    private String title;
    private String author;
}
