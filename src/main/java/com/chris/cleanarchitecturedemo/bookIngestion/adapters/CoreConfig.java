package com.chris.cleanarchitecturedemo.bookIngestion.adapters;

import com.chris.cleanarchitecturedemo.bookIngestion.core.ports.out.BookPersistencePort;
import com.chris.cleanarchitecturedemo.bookIngestion.core.services.SaveBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfig {

    @Bean
    public SaveBookService saveBookUseCase(BookPersistencePort bookPersistencePort) {
        return new SaveBookService(bookPersistencePort);
    }
}
