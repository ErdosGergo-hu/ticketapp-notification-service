package com.erdos.ticketapp.notificationservice.repository;

import com.erdos.ticketapp.notificationservice.model.ProcessedKafkaMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProcessedMessageRepository
        extends JpaRepository<ProcessedKafkaMessage, UUID> {

    @Modifying
    @Query(value = """
        INSERT INTO processed_kafka_messages (
            message_id,
            processed_at
        )
        VALUES (
            :messageId,
            CURRENT_TIMESTAMP
        )
        ON CONFLICT DO NOTHING
        """, nativeQuery = true)
    int insertIfAbsent(@Param("messageId") UUID messageId);
}