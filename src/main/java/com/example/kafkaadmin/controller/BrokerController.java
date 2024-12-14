
package com.example.kafkaadmin.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.example.kafkaadmin.dto.request.ConfigModifyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kafkaadmin.annotation.V1Version;
import com.example.kafkaadmin.dto.Entry;
import com.example.kafkaadmin.service.BrokerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@V1Version
@RestController
public class BrokerController {

    private final BrokerService brokerService;

    public BrokerController(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    @GetMapping("/brokers")
    Map<String, List<Entry<String, String>>> getBrokerConfigs() throws ExecutionException,  InterruptedException {
        return this.brokerService.getAllConfigs();

    }

    @GetMapping("/brokers/{nodeId}")
    List<Entry<String, String>> getBrokerConfig(@PathVariable String nodeId)
            throws ExecutionException, InterruptedException {
        return this.brokerService.getConfig(nodeId);
    }

    @PutMapping("/broker/{nodeId}")
    boolean updateBrokerConfig(@PathVariable String nodeId, @RequestBody ConfigModifyRequest configModifyRequest)
            throws ExecutionException, InterruptedException {

        return this.brokerService.updateConfig(nodeId,configModifyRequest);

    }

}
