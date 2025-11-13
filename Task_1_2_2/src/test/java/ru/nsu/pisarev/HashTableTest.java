package ru.nsu.pisarev;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class HashTableTest {

    private HashTable<String, Number> table;

    @BeforeEach
    void setUp() {
        table = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        table.put("one", 1);
        table.put("two", 2);
        assertEquals(1, table.get("one"));
        assertEquals(2, table.get("two"));
    }

    @Test
    void testPutDuplicateKeyThrows() {
        table.put("one", 1);
        assertThrows(IllegalArgumentException.class, () -> table.put("one", 100));
    }

    @Test
    void testUpdateExistingKey() {
        table.put("x", 10);
        table.update("x", 15);
        assertEquals(15, table.get("x"));
    }

    @Test
    void testUpdateNonExistingKeyThrows() {
        assertThrows(NoSuchElementException.class, () -> table.update("y", 99));
    }

    @Test
    void testRemoveKey() {
        table.put("a", 1);
        table.put("b", 2);
        assertEquals(2, table.remove("b"));
        assertNull(table.get("b"));
        assertEquals(1, table.size());
    }

    @Test
    void testRemoveNonExistingKeyReturnsNull() {
        assertNull(table.remove("missing"));
    }

    @Test
    void testContainsKey() {
        table.put("k", 42);
        assertTrue(table.containsKey("k"));
        assertFalse(table.containsKey("x"));
    }

    @Test
    void testResizeIncreasesCapacity() {
        for (int i = 0; i < 50; i++)
            table.put("k" + i, i);
        assertTrue(table.size() >= 50);
        assertEquals(50, table.size());
        assertNotNull(table.get("k25"));
    }

    @Test
    void testEqualsAndHashCode() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();
        t1.put("a", 1);
        t1.put("b", 2);
        t2.put("a", 1);
        t2.put("b", 2);
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testEqualsDifferentSize() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();
        t1.put("x", 1);
        t1.put("y", 2);
        t2.put("x", 1);
        assertNotEquals(t1, t2);
    }

    @Test
    void testEqualsDifferentValues() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();
        t1.put("x", 1);
        t2.put("x", 2);
        assertNotEquals(t1, t2);
    }

    @Test
    void testToStringFormat() {
        table.put("a", 10);
        table.put("b", 20);
        String s = table.toString();
        assertTrue(s.contains("a="));
        assertTrue(s.contains("b="));
        assertTrue(s.startsWith("{") && s.endsWith("}"));
    }

    @Test
    void testIteratorTraversesAllEntries() {
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        Set<String> keys = new HashSet<>();
        for (Map.Entry<String, Number> e : table) {
            keys.add(e.getKey());
        }

        assertEquals(Set.of("one", "two", "three"), keys);
    }

    @Test
    void testIteratorRemoveRemovesEntry() {
        table.put("a", 1);
        table.put("b", 2);
        Iterator<Map.Entry<String, Number>> it = table.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Number> e = it.next();
            if (e.getKey().equals("a")) {
                it.remove();
            }
        }
        assertFalse(table.containsKey("a"));
        assertTrue(table.containsKey("b"));
    }

    @Test
    void testIteratorConcurrentModificationThrows() {
        table.put("x", 1);
        Iterator<Map.Entry<String, Number>> it = table.iterator();
        table.put("y", 2);
        assertThrows(ConcurrentModificationException.class, it::hasNext);
    }

    @Test
    void testIteratorNextWithoutHasNextThrows() {
        assertThrows(NoSuchElementException.class, () -> table.iterator().next());
    }

    @Test
    void testIteratorRemoveWithoutNextThrows() {
        table.put("k", 1);
        Iterator<Map.Entry<String, Number>> it = table.iterator();
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test
    void testHashEntryEqualsAndHashCode() {
        HashEntry<String, Integer> e1 = new HashEntry<>("k", 5, null);
        HashEntry<String, Integer> e2 = new HashEntry<>("k", 5, null);
        assertEquals(e1, e2);
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    void testHashEntrySetValue() {
        HashEntry<String, Integer> e = new HashEntry<>("x", 10, null);
        assertEquals(10, e.getValue());
        e.setValue(20);
        assertEquals(20, e.getValue());
    }

    @Test
    void testIsEmptyAndSize() {
        assertTrue(table.isEmpty());
        table.put("a", 1);
        assertFalse(table.isEmpty());
        assertEquals(1, table.size());
    }

    @Test
    void testNullKeyThrows() {
        assertThrows(NullPointerException.class, () -> table.put(null, 1));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }
}

