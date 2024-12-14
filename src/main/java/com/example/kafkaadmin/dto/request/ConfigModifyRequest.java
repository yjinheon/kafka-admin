package com.example.kafkaadmin.dto.request;


import lombok.NonNull;
import java.util.Map;


public record ConfigModifyRequest(@NonNull Map<String, String> config) {


}


