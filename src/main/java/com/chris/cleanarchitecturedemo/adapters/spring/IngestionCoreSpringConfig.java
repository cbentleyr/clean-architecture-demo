package com.chris.cleanarchitecturedemo.adapters.spring;

import com.chris.cleanarchitecturedemo.core.ingestion.ports.inbound.SaveBookUseCase;
import com.chris.cleanarchitecturedemo.core.ingestion.ports.outbound.PersistBookPort;
import com.chris.cleanarchitecturedemo.core.ingestion.SaveBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngestionCoreSpringConfig {

    @Bean
    public SaveBookUseCase saveBookUseCase(PersistBookPort persistBookPort) {
        return new SaveBookService(persistBookPort);
    }
}
