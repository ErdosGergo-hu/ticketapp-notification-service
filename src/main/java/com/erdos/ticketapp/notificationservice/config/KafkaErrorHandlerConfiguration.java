package com.erdos.ticketapp.notificationservice.config;

import com.erdos.ticketapp.notificationservice.exception.InvalidNotificationEventException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

// TODO: Add hozáá egy DLT consumert, és tárold is
@Configuration
public class KafkaErrorHandlerConfiguration {

    @Bean
    public DefaultErrorHandler kafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(
                kafkaTemplate,
                (record, exception) ->
                        new TopicPartition(
                                record.topic() + ".DLT",
                                record.partition()
                        )
        );

        // Első feldolgozás + 3 újrapróbálkozás, retry-k között 2 másodperc.
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                recoverer,
                new FixedBackOff(2_000L, 3L)
        );

        // Ezeken nem segít az újrapróbálkozás.
        errorHandler.addNotRetryableExceptions(
                InvalidNotificationEventException.class
        );

        return errorHandler;
    }
}