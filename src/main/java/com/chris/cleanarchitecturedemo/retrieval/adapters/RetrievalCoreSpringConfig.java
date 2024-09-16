package com.chris.cleanarchitecturedemo.retrieval.adapters;

import com.chris.cleanarchitecturedemo.retrieval.core.RetrieveBookService;
import com.chris.cleanarchitecturedemo.retrieval.core.ports.inbound.RetrieveBookUseCase;
import com.chris.cleanarchitecturedemo.retrieval.core.ports.outbound.RetrieveBookPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrievalCoreSpringConfig {

    @Bean
    public RetrieveBookUseCase retrieveBookUseCase(RetrieveBookPort retrieveBookPort) {
        return new RetrieveBookService(retrieveBookPort);
    }
}
