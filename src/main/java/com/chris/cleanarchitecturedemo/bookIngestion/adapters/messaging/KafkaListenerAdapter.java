package com.chris.cleanarchitecturedemo.bookIngestion.adapters.messaging;

import com.chris.cleanarchitecturedemo.bookIngestion.core.ports.in.SaveBookUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaListenerAdapter {

    private final SaveBookUseCase saveBookUseCase;

    @KafkaListener(id = "clean-arch-listener", topics = "books",
            properties = {"spring.json.value.default.type=com.chris.cleanarchitecturedemo.bookIngestion.adapters.messaging.BookMessage"})
    public void listen(BookMessage message) {
        log.info("Consumed book with title: {}, and author: {}.",
                message.title(), message.author());
        var command = SaveBookUseCase.SaveBookCommand.builder()
                .title(message.title())
                .author(message.author())
                .build();
        saveBookUseCase.saveBook(command);
    }
}
