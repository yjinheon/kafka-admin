package com.example.kafkaadmin.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AdminProperties.class)
public class KafkaAdminClientConfig {

    private final AdminProperties adminProperties;

    public KafkaAdminClientConfig(AdminProperties adminProperties) {
        this.adminProperties = adminProperties;
    }

    @Bean
    AdminClient kafkaAdminClient() {

        Map<String, Object> props = new HashMap<>();
        props.put(AdminClientConfig.DEFAULT_API_TIMEOUT_MS_CONFIG, 10_000); // 10s
        props.put(AdminClientConfig.REQUEST_TIMEOUT_MS_CONFIG, 10_000); // 10s
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.adminProperties.getServers());

        return AdminClient.create(props);

    }

}
