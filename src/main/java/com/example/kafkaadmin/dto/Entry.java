
package com.example.kafkaadmin.dto;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Entry<K, V> {
    K key;
    V value;

    // static factory method
    public static <K, V> Entry<K, V> of(K key, V value) {
        return new Entry<>(key, value);
    }

}
