package com.example.kafkaadmin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.example.kafkaadmin.dto.ConsumerGroup;
import com.example.kafkaadmin.dto.ConsumerGroupId;
import com.example.kafkaadmin.dto.OffsetAndMetadataRecord;
import com.example.kafkaadmin.dto.request.OffsetChangeRequest;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class ConsumerGroupService extends AbstractKafkaAdminClientService {
    public ConsumerGroupService(AdminClient kafkaAdminClient) {
        super(kafkaAdminClient);
    }

    public List<ConsumerGroupId> getAll() throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listConsumerGroups()
                .all()
                .thenApply(it -> it.stream()
                        .map(ConsumerGroupId::from)
                        .collect(Collectors.toList()))
                .get();
    }

    public Map<String, ConsumerGroup> get(List<String> groupIds) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.describeConsumerGroups(groupIds)
                .all()
                .thenApply(it -> it.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> ConsumerGroup.from(e.getValue()))))
                .get();
    }

    public List<Map<TopicPartition, OffsetAndMetadata>> getOffset(
            String groupId) throws ExecutionException, InterruptedException {
        return this.kafkaAdminClient.listConsumerGroupOffsets(groupId)
                .all()
                .thenApply(it -> it
                        .values()
                        .stream()
                        .map(Map::entrySet)
                        .map(entries -> entries
                                .stream()
                                .collect(
                                        Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue)))
                        .collect(Collectors.toList()))
                .get();
    }

    public boolean updateOffsetAndMetadata(
            String groupId, OffsetChangeRequest offsetChangeRequest)
            throws ExecutionException, InterruptedException {
        Map<TopicPartition, OffsetAndMetadata> offsets = offsetChangeRequest
                .offsets()
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                it -> this.getTopicPartition(it.getKey()),
                                it -> this.convertOffsetAndMetadata(it.getValue())));

        this.kafkaAdminClient.alterConsumerGroupOffsets(groupId, offsets)
                .all()
                .get();

        return true;
    }

    private OffsetAndMetadata convertOffsetAndMetadata(OffsetAndMetadataRecord custom) {
        return new OffsetAndMetadata(
                custom.offset(),
                custom.leaderEpoch() != null ? Optional.of(custom.leaderEpoch()) : Optional.empty(),
                custom.metadata());
    }

    private TopicPartition getTopicPartition(String topicPartitionString) {
        final String separator = "-";
        String[] splitKeys = topicPartitionString.split(separator);
        return new TopicPartition(splitKeys[0], Integer.parseInt(splitKeys[1]));
    }

    private OffsetAndMetadataRecord convertOffsetAndMetadataRecord(OffsetAndMetadataRecord offsetAndMetadataRecord) {
        return OffsetAndMetadataRecord.from(offsetAndMetadataRecord);

    }

    public boolean delete(String groupIds) throws ExecutionException, InterruptedException {
        this.kafkaAdminClient.deleteConsumerGroups(List.of(groupIds))
                .all()
                .get();

        return true;
    }
}
