package com.example.kafkaadmin.dto.request;

import com.example.kafkaadmin.dto.OffsetAndMetadataRecord;

import java.util.Map;

public record OffsetChangeRequest(
        Map<String, OffsetAndMetadataRecord> offsets

) {
}
