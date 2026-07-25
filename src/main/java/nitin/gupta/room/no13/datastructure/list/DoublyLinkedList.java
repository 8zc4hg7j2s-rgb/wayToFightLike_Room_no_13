package nitin.gupta.room.no13.datastructure.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Tolerate;

public class DoublyLinkedList<T extends Comparable<T>> {
    private Node<T> head, tail;

    @ToString
    @EqualsAndHashCode
    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> next, prev;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
    
}
