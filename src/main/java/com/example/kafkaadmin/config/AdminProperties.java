package com.example.kafkaadmin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Value;
import java.util.List;

@Value
@ConfigurationProperties("kafka.admin")
public class AdminProperties {
    List<String> servers;

}
