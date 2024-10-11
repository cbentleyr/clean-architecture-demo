package com.chris.cleanarchitecturedemo.utils;

import com.chris.cleanarchitecturedemo.ingestion.core.domain.Book;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.testcontainers.kafka.ConfluentKafkaContainer;

import java.util.HashMap;

public class KafkaIntegrationTestHelper {

    public static ConfluentKafkaContainer buildKafkaContainer() {
        return new ConfluentKafkaContainer("confluentinc/cp-kafka:7.4.0");
    }

    public static void produceValidBook(Book book, String bootstrapServer) {
        var producer = congfigureProducer(bootstrapServer);
        producer.send(new ProducerRecord<>("books",
                String.format("{ \"title\":  \"%s\", \"author\":  \"%s\" }", book.title(), book.author())));
        producer.close();
    }

    public static void produceMalformedMessage(String bootstrapServer) {
        var producer = congfigureProducer(bootstrapServer);
        producer.send(new ProducerRecord<>("books", "not a valid book"));
        producer.close();
    }

    public static Producer<String, String> congfigureProducer(String bootstrapServers) {
        var config = new HashMap<String, Object>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.LINGER_MS_CONFIG, 0);
        return new KafkaProducer<>(config);
    }
}
