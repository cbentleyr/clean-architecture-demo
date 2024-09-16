package com.chris.cleanarchitecturedemo.ingestion.adapters.messaging;

import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.NonTransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.exceptions.TransientPersistenceException;
import com.chris.cleanarchitecturedemo.ingestion.core.ports.inbound.SaveBookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListenerAdapter {

    private final SaveBookUseCase saveBookUseCase;

    @KafkaListener(id = "clean-arch-listener", topics = "books",
            properties = {
                "spring.json.value.default.type=com.chris.cleanarchitecturedemo.ingestion.adapters.messaging.BookMessage",
                "spring.json.use.type.headers=false"
            })
    public void listen(BookMessage message) {
        var command = SaveBookUseCase.SaveBookCommand.builder()
                .title(message.title())
                .author(message.author())
                .build();
        try {
            saveBookUseCase.saveBook(command);
        } catch (TransientPersistenceException exception) {
            // Error handling for recoverable messages -> replay after backoff
        } catch (NonTransientPersistenceException exception) {
            // Error handling for irrecoverable messages -> produce to DLQ, commit offset and move on
        }
    }
}
