package ru.nsu.pisarev;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Iterator for the HashTable, supports fail-fast behavior.
 */
public class HashTableIterator<K, V> implements Iterator<Map.Entry<K, V>> {
    private final HashTable<K, V> table;
    private int expectedModCount;
    private int bucketIndex;
    private HashEntry<K, V> current;
    private HashEntry<K, V> lastReturned;

    public HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        this.expectedModCount = table.modCount;
        this.bucketIndex = -1;
        this.current = null;
        this.lastReturned = null;
        advance();
    }

    private void checkForCoModification() {
        if (expectedModCount != table.modCount)
            throw new ConcurrentModificationException();
    }

    private void advance() {
        if (current != null && current.next != null) {
            current = current.next;
            return;
        }
        current = null;
        bucketIndex++;
        while (bucketIndex < table.getTable().length) {
            if (table.getTable()[bucketIndex] != null) {
                current = table.getTable()[bucketIndex];
                return;
            }
            bucketIndex++;
        }
    }

    @Override
    public boolean hasNext() {
        checkForCoModification();
        return current != null;
    }

    @Override
    public Map.Entry<K, V> next() {
        checkForCoModification();
        if (current == null)
            throw new NoSuchElementException();
        lastReturned = current;
        Map.Entry<K, V> res = current;
        advance();
        return res;
    }

    @Override
    public void remove() {
        checkForCoModification();
        if (lastReturned == null)
            throw new IllegalStateException();
        if (table.remove(lastReturned.key) == null)
            throw new NoSuchElementException();
        expectedModCount = table.modCount;
        lastReturned = null;
    }
}
