package ru.nsu.pisarev;

import java.util.Map;
import java.util.Objects;

/**
 * Represents a single node in the hash table (a linked list element in a bucket).
 */
public class HashEntry<K, V> implements Map.Entry<K, V> {
    final K key;
    V value;
    HashEntry<K, V> next;

    public HashEntry(K key, V value, HashEntry<K, V> next) {
        this.key = Objects.requireNonNull(key, "Key must not be null");
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        return Objects.equals(key, ((Map.Entry<?, ?>) o).getKey()) && Objects.equals(value,  ((Map.Entry<?, ?>) o).getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }
}


