package com.example.kafkaadmin.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public enum Converter {
    BASIC {
        @Override
        public ObjectMapper getMapper() {
            return om;
        }
    };

    public abstract ObjectMapper getMapper();

    private static final ObjectMapper om = new ObjectMapper();

}
