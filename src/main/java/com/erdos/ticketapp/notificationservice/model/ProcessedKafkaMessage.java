package com.erdos.ticketapp.notificationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_kafka_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedKafkaMessage {

    @Id
    private UUID messageId;

    @Column(nullable = false)
    private OffsetDateTime processedAt;
}