package com.chris.cleanarchitecturedemo.ingestion.adapters;

import com.chris.cleanarchitecturedemo.ingestion.core.ports.inbound.SaveBookUseCase;
import com.chris.cleanarchitecturedemo.ingestion.core.ports.outbound.PersistBookPort;
import com.chris.cleanarchitecturedemo.ingestion.core.SaveBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngestionCoreSpringConfig {

    @Bean
    public SaveBookUseCase saveBookUseCase(PersistBookPort persistBookPort) {
        return new SaveBookService(persistBookPort);
    }
}
