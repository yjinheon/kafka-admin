package com.example.kafkaadmin.dto;


public record OffsetAndMetadataRecord (
        long offset,
        String metadata,
        Integer leaderEpoch  // use null to represent the absence of a leader
) {
    public static OffsetAndMetadataRecord from(OffsetAndMetadataRecord offsetAndMetadata) {
        return new OffsetAndMetadataRecord(
                offsetAndMetadata.offset(),
                offsetAndMetadata.metadata(),
                offsetAndMetadata.leaderEpoch()
        );
    }
}
