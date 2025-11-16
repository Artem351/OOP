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


public class HashTableTests {

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


    /* Test large HashTable*/
    @Test
    void testLargeIterationCompleteness() {
        HashTable<String, Integer> table = new HashTable<>();

        int N = 200_000;
        for (int i = 0; i < N; i++) {
            table.put("k" + i, i);
        }

        boolean[] seen = new boolean[N];
        int count = 0;

        for (Map.Entry<String, Integer> e : table) {
            String k = e.getKey();
            assertNotNull(k);

            assertTrue(k.startsWith("k"));
            int idx = Integer.parseInt(k.substring(1));

            assertFalse(seen[idx]);
            seen[idx] = true;
            count++;
        }

        assertEquals(N, count);
        for (boolean b : seen) assertTrue(b);
    }


    @Test
    void testIteratorAfterMassRemovals() {
        HashTable<String, Integer> table = new HashTable<>();

        int N = 200_000;
        for (int i = 0; i < N; i++)
            table.put("num" + i, i);


        for (int i = 0; i < N / 2; i++)
            table.remove("num" + i);

        int iterCount = 0;
        for (Map.Entry<String, Integer> e : table) {
            int v = e.getValue();
            assertTrue(v >= N / 2);
            iterCount++;
        }

        assertEquals(N / 2, iterCount);
    }


    @Test
    void testIteratorRemove() {
        HashTable<String, Integer> table = new HashTable<>();

        for (int i = 0; i < 200_000; i++)
            table.put("x" + i, i);

        Iterator<Map.Entry<String, Integer>> it = table.iterator();

        int removed = 0;
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            if (e.getValue() % 100 == 0) {
                it.remove();
                removed++;
            }
        }

        assertEquals(2_000, removed);
        assertEquals(200_000 - 2_000, table.size());

        for (int i = 0; i < 200_000; i++) {
            if (i % 100 == 0)
                assertNull(table.get("x" + i));
            else
                assertEquals(i, table.get("x" + i));
        }
    }


    @Test
    void testFailFastOnModification() {
        HashTable<String, Integer> table = new HashTable<>();
        for (int i = 0; i < 200_000; i++)
            table.put("k" + i, i);

        Iterator<Map.Entry<String, Integer>> it = table.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
            table.remove("k2000");
            it.next();
        });
    }


    @Test
    void testFailFastOnPut() {
        HashTable<String, Integer> table = new HashTable<>();
        for (int i = 0; i < 200_000; i++)
            table.put("z" + i, i);

        Iterator<Map.Entry<String, Integer>> it = table.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
            table.put("newKey", 123);
            it.hasNext();
        });
    }
}

