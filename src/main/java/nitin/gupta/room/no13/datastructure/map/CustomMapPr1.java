package nitin.gupta.room.no13.datastructure.map;

import lombok.EqualsAndHashCode;

public class CustomMapPr1<K extends Comparable<K>, V> {
    private final int size;
    private transient Entry<K, V>[] table;
    public CustomMapPr1(int size) {
        this.size = size;
        table = new Entry[size];
    }

    private int hash(K key) {
        return key.hashCode() % size;
    }

    public void put(K key, V value) {
        Entry<K, V> entry = new Entry<>(key, value, null);
        int hash = hash(key);
        if (table[hash] == null) {
            table[hash] = entry;
        } else {
            Entry<K, V> oldEntry = table[hash];
            Entry<K, V> previous = null;
            while (oldEntry != null) {
                if (oldEntry.key.compareTo(key) == 0) {
                    if (previous == null) {
                        entry.next = oldEntry.next;
                        table[hash] = entry;
                        return;
                    } else {
                        entry.next = oldEntry.next;
                        previous.next = entry;
                        return;
                    }
                }
                previous = oldEntry;
                oldEntry = oldEntry.next;
            }
            previous.next = entry;
        }
    }

    public V get(K key) {
        Entry<K, V> entry = table[hash(key)];
        while (entry != null) {
            if (entry.key.compareTo(key) == 0) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    public void printAll() {
        for (int i = 0; i < table.length; i++) {
            Entry<K, V> entry = table[i];
            while (entry != null) {
                System.out.print(entry);
                entry = entry.next;
            }
        }
        System.out.println();
    }

    public void remove(K key) {
        Entry<K, V> entry = table[hash(key)];
        while (entry != null) {
            if (entry.key.compareTo(key) == 0) {
                table[hash(key)] = entry.next;
                return;
            }
        }
    }

    @EqualsAndHashCode
    private static class Entry<K extends Comparable<K>, V> {
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return this.key + "=" + this.value + ", ";
        }
    }
}
