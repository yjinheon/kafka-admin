package com.example.kafkaadmin.controller;

import com.example.kafkaadmin.annotation.V1Version;
import com.example.kafkaadmin.dto.ConsumerGroup;
import com.example.kafkaadmin.dto.ConsumerGroupId;
import com.example.kafkaadmin.dto.OffsetAndMetadataRecord;
import com.example.kafkaadmin.dto.request.OffsetChangeRequest;
import com.example.kafkaadmin.service.ConsumerGroupService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@V1Version
@RestController
@RequiredArgsConstructor
public class ConsumerGroupController {
    private final ConsumerGroupService consumerGroupService;

    @GetMapping("consumer-group-ids")
    List<ConsumerGroupId> getConsumerGroupIds() throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getAll();
    }

    @GetMapping("consumer-groups/{groupId}")
    ConsumerGroup getConsumerGroupInfo(@PathVariable String groupId) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(List.of(groupId))
                .get(groupId);
    }

    @GetMapping("consumer-groups")
    Map<String, ConsumerGroup> getConsumerGroupInfos(@RequestParam List<String> groupIds) throws ExecutionException, InterruptedException, TimeoutException {
        return this.consumerGroupService.get(groupIds);
    }

    @GetMapping("consumer-groups/{groupId}/offsets")
    List<Map<TopicPartition, OffsetAndMetadata>> getConsumerGroupOffsets(@PathVariable  String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.getOffset(groupId);
    }

    @DeleteMapping("consumer-groups/{groupId}")
    boolean deleteConsumerGroup(@PathVariable String groupId) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.delete(groupId);
    }

    @PutMapping("consumer-groups/{groupId}")
    boolean updateConsumerGroupOffsetAndMetadata(@PathVariable String groupId, @RequestBody OffsetChangeRequest offsetChangeRequest) throws ExecutionException, InterruptedException {
        return this.consumerGroupService.updateOffsetAndMetadata(groupId, offsetChangeRequest);
    }
}
