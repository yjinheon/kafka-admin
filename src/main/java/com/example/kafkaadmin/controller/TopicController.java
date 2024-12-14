package com.example.kafkaadmin.controller;

import com.example.kafkaadmin.annotation.V1Version;
import com.example.kafkaadmin.dto.Entry;
import com.example.kafkaadmin.dto.Topic;
import com.example.kafkaadmin.dto.request.ConfigModifyRequest;
import com.example.kafkaadmin.dto.request.CreateTopicRequest;
import com.example.kafkaadmin.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@V1Version
@RestController
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("topics")
    boolean createTopic(CreateTopicRequest createTopicRequest) throws ExecutionException, InterruptedException {
        return this.topicService.create(createTopicRequest);
    }

    @GetMapping("topic-names")
    Set<String> getAllTopicNames() throws ExecutionException, InterruptedException {
        return this.topicService.getAll();
    }

    @GetMapping("topics/{topicName}")
    Topic getTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.get(List.of(topicName))
                .get(topicName);
    }

    @GetMapping("topics")
    Map<String, Topic> getTopics1(@RequestParam List<String> topicNames) throws ExecutionException, InterruptedException {
        return this.topicService.get(topicNames);
    }

    @DeleteMapping("topics/{topicName}")
    boolean deleteTopic(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.delete(topicName);
    }

    @PutMapping("/topics/{topicName}")
    boolean updateTopicConfig(@PathVariable String topicName, @RequestBody ConfigModifyRequest configModifyRequest) throws ExecutionException, InterruptedException {
        return this.topicService.updateConfig(topicName, configModifyRequest);
    }

    @GetMapping("topic-configs/{topicName}")
    List<Entry<String, String>> getTopicConfig(@PathVariable String topicName) throws ExecutionException, InterruptedException {
        return this.topicService.getConfig(topicName);
    }
}
