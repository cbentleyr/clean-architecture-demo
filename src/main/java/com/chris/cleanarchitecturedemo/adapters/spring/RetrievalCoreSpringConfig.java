package com.chris.cleanarchitecturedemo.adapters.spring;

import com.chris.cleanarchitecturedemo.core.retrieval.RetrieveBookService;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.inbound.RetrieveBookUseCase;
import com.chris.cleanarchitecturedemo.core.retrieval.ports.outbound.RetrieveBookPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RetrievalCoreSpringConfig {

    @Bean
    public RetrieveBookUseCase retrieveBookUseCase(RetrieveBookPort retrieveBookPort) {
        return new RetrieveBookService(retrieveBookPort);
    }
}
