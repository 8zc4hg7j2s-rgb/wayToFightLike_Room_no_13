package nitin.gupta.room.no13.datastructure.map;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Hashtable;
import java.util.Map;

public class CustomMap<K extends Comparable<K>, V> {
    private final int capacity;
    private final transient Entry<K, V>[] table;

    public CustomMap(int capacity) {
        this.capacity = capacity;
        table = new Entry[capacity];
    }

    @EqualsAndHashCode
    @ToString
    private static class Entry<K extends Comparable<K>, V> {
        final K key;
        V value;
        Entry<K, V> next;
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public int getHashCodeValue(K key) {
        return key.hashCode() % capacity;
    }

    public void put(K key, V value) {
        Entry<K, V> newEntry = new Entry<>(key, value, null);
        int hash = getHashCodeValue(key);
        if (table[hash] == null) {
            table[hash] = newEntry;
        } else {
            Entry<K, V> oldEntry = table[hash];
            Entry<K, V> previous = null;
            while (oldEntry != null) {
                if (oldEntry.key.compareTo(key) == 0) {
                    if (previous == null) {
                        newEntry.next = oldEntry.next;
                        table[hash] = newEntry;
                        return;
                    } else {
                        newEntry.next = oldEntry.next;
                        previous.next = newEntry;
                        return;
                    }
                }
                previous = oldEntry;
                oldEntry = oldEntry.next;
            }
            previous.next = newEntry;
        }
    }

    public void remove(K searchKey){
        int hashCodeValue= getHashCodeValue(searchKey);
        Entry<K,V> previous =table[hashCodeValue];
        Entry<K,V> current =previous;
        while(current!=null){
            Entry<K,V> next =current.next;
            if(current.key.compareTo(searchKey)==0){
                if (previous == next) {
                    table[hashCodeValue] = next;
                } else {
                    previous.next = next;
                }
            }
            previous=current;
            current=next;
        }
    }

    public V get(K searchKey){
        int hashCodeValue= getHashCodeValue(searchKey);
        Entry<K,V> node =table[hashCodeValue];
        while (node!=null){
            if(node.key.compareTo(searchKey)==0)
                return node.value;;
            node=node.next;
        }
        return null;
    }

    public void printAll(){
        Entry<K,V>[] tmp =table ;
        for (int i = 0; i < tmp.length; i++) {
            Entry<K,V> current=tmp[i];
            while(current!=null){
                System.out.print(current);
                current=current.next;
            }
            System.out.println();
        }
    }

}



