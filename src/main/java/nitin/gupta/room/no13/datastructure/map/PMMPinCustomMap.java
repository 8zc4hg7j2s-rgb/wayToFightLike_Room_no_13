package nitin.gupta.room.no13.datastructure.map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

public class PMMPinCustomMap<K extends Comparable<K>, V> {
    private final int capacity;
    private Entry[] table;

    public PMMPinCustomMap(int capacity) {
        this.capacity = capacity;
        this.table = new Entry[capacity];
    }

    public void put(K key, V value) {
        int index = getHashCodeValue(key);
        Entry<K, V> newEntry = new Entry<>(key, value, null);
        if (table[index] == null) {
            table[index] = newEntry;
        } else {
            Entry<K, V> entry = table[index];
            while (entry != null) {
                if (entry.key.equals(key)) {
                    entry.value = value;
                    return;
                }
                if (entry.next == null) {
                    entry.next = newEntry;
                    return;
                }
                entry = entry.next;
            }
        }
    }

    public int getHashCodeValue(K key) {
        return key.hashCode() % capacity;
    }

    public V get(K key) {
        Entry<K, V> entry = table[getHashCodeValue(key)];
        while (entry != null) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public void remove(K key) {
        int index = getHashCodeValue(key);
        Entry<K, V> entry = table[index];
        if (entry != null) {
            while (entry != null) {
                if (entry.key.equals(key)) {
                    entry.next = entry.next.next;
                }
                entry = entry.next;
            }
        }
    }

    public void clear() {
        this.table = null;
    }

    public void printAll() {
        Entry[] bucket = table;
        for (int i = 0; i < bucket.length; i++) {
            Entry entry = bucket[i];
            while (entry != null) {
                System.out.println(entry.key + " : " + entry.value);
                entry = entry.next;
            }
        }
    }

    @ToString
    @EqualsAndHashCode
    static class Entry<K extends Comparable<K>, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}


