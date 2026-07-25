package nitin.gupta.room.no13.datastructure.list;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

public class DoublyLinkedList<T extends Comparable<T>> {

    private Node<T> head, tail;

    public Node<T> getHead() {
        return head;
    }

    public Node<T> getTail() {
        return tail;
    }

    //– add node at the beginning
    public void insertAtHead(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        newNode.next = head;   // Point new node's next to the current head
        head.prev = newNode;   // Point current head's prev back to the new node
        head = newNode;        // Move head pointer to the new node
    }

    //– add node at the end
    public void insertAtTail(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = tail = newNode;
        } else {
            Node<T> current = tail;
            current.next = newNode;
            newNode.prev = current;
            tail = newNode;
        }
    }

    //– add node at a specific index
    public void insertAtPosition(T value, int index) {
        Node<T> newNode = new Node<>(value);
        if (index == 0) {
            insertAtHead(value);
        } else {
            Node<T> current = head;
            int i = 0;
            for (; i < index && current != null; i++) {
                current = current.next;
            }
            if (i == index && current != null) {
                current.next = newNode;
                newNode.prev = current;
            } else if (i == index && current == null) {
                insertAtTail(value);
            } else throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    public void print() {
        Node<T> current = head;
        while (current != null) {
            System.out.print(current);
            current = current.next;
        }
        System.out.println();
    }

    public void printReverse() {
        Node<T> current = tail;
        while (current != null) {
            System.out.print(current);
            current = current.prev;
        }
        System.out.println();
    }

    //– insert new node before a given node
    public void insertBefore(Node<T> node, T value) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }

        Node<T> newNode = new Node<>(value);
        Node<T> prevNode = node.prev;
        newNode.next = node;
        newNode.prev = prevNode;
        node.prev = newNode;

        if (prevNode != null) {
            prevNode.next = newNode;
        } else {
            // node was the head
            head = newNode;
        }
    }

    public Node<T> getMiddle() {
        Objects.requireNonNull(head, "head is null");
        Node<T> slow = head;
        Node<T> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    //– insert new node after a given node
    public void insertAfter(Node<T> node, T value) {
        if (node == null) {
            throw new IllegalArgumentException("node cannot be null");
        }
        Node<T> newNode = new Node<>(value);
        Node<T> nextNode = node.next;

        newNode.next = nextNode;
        newNode.prev = node;
        node.next = newNode;

        if (nextNode != null) {
            nextNode.prev = newNode;
        } else {
            // node was the tail
            tail = newNode;
        }


    }

    //– remove first node
    public void deleteAtHead() {
        if (head == null) return;
        head = head.next;
        if (head != null) head.prev=null;
        else tail=null;
    }

    //7. deleteAtTail() – remove last node
//8. deleteAtPosition(index) – remove node at a specific index
//9. deleteByValue(value) – remove first node matching a value
//10. deleteNode(node) – remove a given node reference
//
//            Traversal
//11. traverseForward() – print/iterate from head to tail
//12. traverseBackward() – print/iterate from tail to head
//
//    Search/access
//13. search(value) – check if value exists, return node/index
//14. getAt(index) – get value/node at index
//15. indexOf(value) – find index of a value
//
//            Utility
//16. isEmpty() – check if list has zero nodes
//17. size() / length() – count of nodes
//18. reverse() – reverse the list in place
//19. clear() – remove all nodes
//20. toArray() – convert list to array/list structure
//
//    Bonus (often included too)
//
//    getHead() / getTail() – return head/tail node
//    updateAt(index, value) – modify value at index
//    contains(value) – boolean existence check
    static class Node<T extends Comparable<T>> {
        T data;
        Node<T> next, prev;

        Node(T data) {
            this.data = data;
        }

        Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        public String toString() {
            return " " + data;
        }
    }
}

