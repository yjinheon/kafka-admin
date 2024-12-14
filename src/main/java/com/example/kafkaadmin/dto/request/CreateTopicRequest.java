package com.example.kafkaadmin.dto.request;
import java.util.Objects;

public record CreateTopicRequest(
        String topicName,
        int numPartitions,
        Integer replicationFactor
) {
    // static factory method
    public static CreateTopicRequest of(String topicName, int numPartitions, Integer replicationFactor) {
        Objects.requireNonNull(topicName, "topicName must not be null");
        Objects.requireNonNull(replicationFactor, "replicationFactor must not be null");

        return new CreateTopicRequest(topicName, numPartitions, replicationFactor);
    }

    public CreateTopicRequest {}
}
