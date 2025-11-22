package ru.nsu.pisarev;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A generic hash table implementation supporting basic CRUD operations,
 * iteration, and equality comparison.
 */
public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private HashEntry<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int threshold;
    int modCount;

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be > 0");
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Invalid load factor");
        }

        this.table = (HashEntry<K, V>[]) new HashEntry[roundUpToPowerOfTwo(initialCapacity)];
        this.loadFactor = loadFactor;
        this.threshold = (int) (table.length * loadFactor);
        this.size = 0;
        this.modCount = 0;
    }


    public HashTable() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashEntry<K, V>[] getTable() {
        return table;
    }


    /* Adds a new key-value pair. Throws exception if key already exists. */
    public void put(K key, V value) {
        Objects.requireNonNull(key, "Key must not be null");
        ensureCapacity();
        int idx = indexFor(key, table.length);
        for (HashEntry<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                throw new IllegalArgumentException("Key already exists: " + key);
            }
        }
        table[idx] = new HashEntry<>(key, value, table[idx]);
        size++;
        modCount++;
    }

    /* Updates the value for a key. Throws exception if key not found. */
    public void update(K key, V value) {
        Objects.requireNonNull(key, "Key must not be null");
        int idx = indexFor(key, table.length);
        for (HashEntry<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                modCount++;
                return;
            }
        }
        throw new NoSuchElementException("Key not found: " + key);
    }

    /* Returns the value for a key, or null if not found. */
    public V get(Object key) {
        Objects.requireNonNull(key, "Key must not be null");
        int idx = indexFor(key, table.length);
        for (HashEntry<K, V> e = table[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return null;
    }

    /* Removes a key-value pair and returns the old value (or null if not found). */
    public V remove(K key) {
        Objects.requireNonNull(key, "Key must not be null");
        int idx = indexFor(key, table.length);
        HashEntry<K, V> prev = null;
        for (HashEntry<K, V> e = table[idx]; e != null; prev = e, e = e.next) {
            if (e.key.equals(key)) {
                if (prev == null) {
                    table[idx] = e.next;
                }
                else {
                    prev.next = e.next;
                }
                size--;
                modCount++;
                return e.value;
            }
        }
        return null;
    }

    /* Checks if a key exists in the table. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof HashTable<?, ?> other)) {
            return false;
        }
        if (this.size != other.size) {
            return false;
        }

        for (Map.Entry<K, V> e : this) {
            Object otherValue = other.get(e.getKey());
            if (!Objects.equals(e.getValue(), otherValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Map.Entry<K, V> e : this)
            h += e.hashCode();
        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<K, V> e : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(e.getKey()).append("=").append(e.getValue());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }


    private static int roundUpToPowerOfTwo(int v) {
        int r = 1;
        while (r < v)
            r <<= 1;
        return r;
    }

    private int indexFor(Object key, int length) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return h & (length - 1);
    }

    private void ensureCapacity() {
        if (size + 1 > threshold) {
            resize(table.length * 2);
        }
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        HashEntry<K, V>[] old = table;
        HashEntry<K, V>[] nw = (HashEntry<K, V>[]) new HashEntry[newCapacity];

        for (HashEntry<K, V> head : old) {
            while (head != null) {
                HashEntry<K, V> next = head.next;
                int idx = indexFor(head.key, newCapacity);
                head.next = nw[idx];
                nw[idx] = head;
                head = next;
            }
        }
        table = nw;
        threshold = (int) (newCapacity * loadFactor);
        modCount++;
    }
}
