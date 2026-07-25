package nitin.gupta.room.no13.datastructure;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Objects;


public class SingleLinkedList<T extends Comparable<T>> {
    private Node<T> head, tail;

    public void addAll(List<T> list) {
        // 1. Check if the input collection is null or empty
        if (list == null || list.isEmpty()) {
            return;
        } else {
            Node<T> temp = tail;
            for (T element : list) {
                addLast(element);
            }
        }
    }

    public void addLast(T element) {
        Node<T> node = new Node<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            Node<T> temp = tail;
            temp.next = node;
            tail = node;
        }
    }

    public void addAtPosition(int index, T element) {
        Node<T> node = new Node<>(element);
        Node<T> temp = head;
        int count = 0;
        while (count < index - 1 && temp.next != null) {
            temp = temp.next;
            count++;
        }
        if (count == index) {
            node.next = temp;
            head = node;
        } else if (count == index - 1) {
            Node<T> ptr = temp;
            Node<T> ptrNext = temp.next;
            ptr.next = node;
            node.next = ptrNext;
        } else if (count == index - 2) {
            addLast(element);
        } else {
            throw new IllegalArgumentException("Index out of bounds");
        }
    }

    public void addFirst(T element) {
        Node<T> node = new Node<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            Node<T> temp = head;
            node.next = temp;
            head = node;
        }
    }

    public void print() {
        Node<T> temp = head;
        while (temp != null) {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public void removeFirst() {
        if (head == null) {
            return;
        } else {
            Node<T> temp = head;
            temp = temp.next;
            head = temp;
        }
    }

    public void removeLast() {
        if (head == null) {
            return;
        }
        Node<T> temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
    }

    public void clean() {
        head = tail = null;
    }

    public void remove(int index) {
        Node<T> temp = head;
        int count = 1;
        while (count < index - 1 && temp.next != null) {
            temp = temp.next;
            count++;
        }
        temp.next = temp.next.next;
    }

    public void revers() {
        Node prev = null;
        Node curr = head;
        Node nextNode = null;
        while (curr != null) {
            nextNode = curr.next;  // Save next node
            curr.next = prev;      // Reverse the link
            prev = curr;           // Move prev one step forward
            curr = nextNode;       // Move curr one step forward
        }
        head = prev;
    }

    public void removeDuplicate() {
        Node<T> tmp = head;
        while (tmp != null) {
            Node<T> ptr = tmp.next;
            while (ptr != null && ptr.next != null) {
                if (tmp.value.compareTo(ptr.next.value) == 0) {
                    ptr.next = ptr.next.next;
                } else if (ptr.value.compareTo(ptr.next.value) == 0) {
                    ptr.next = ptr.next.next;
                }
                ptr = ptr.next;
            }
            tmp = tmp.next;
        }
    }

    public void kThAddFromLast(T value, int k) {
        Objects.requireNonNull(head);
        Node<T> tmp = head;
        Node<T> s = head;
        int count = 1;
        boolean flag = false;
        while (tmp != null) {
            if (count <= k) {
                count++;
                tmp = tmp.next;
            } else {
                s = s.next;
                tmp = tmp.next;
                flag = true;
            }
        }
        if (flag == true) {
            Node<T> current = s;
            Node<T> currentToNext = s.next;
            Node<T> newNode = new Node<>(value);
            current.next = newNode;
            newNode.next = currentToNext;
        } else throw new IndexOutOfBoundsException("exceed limit");
    }

    public static Node mergeSort(Node head) {
        // Base case: if list is empty or has only one node, it is already sorted
        if (head == null || head.next == null) {
            return head;
        }

        // 1. Split the list into two halves
        Node middle = getMiddle(head);
        Node nextToMiddle = middle.next;
        middle.next = null; // Break the link to separate into two sublists

        // 2. Recursively sort both halves
        Node left = mergeSort(head);
        Node right = mergeSort(nextToMiddle);

        // 3. Merge the sorted halves
        return sortedMerge(left, right);
    }

    // Helper function to merge two sorted linked lists
    private static Node sortedMerge(Node a, Node b) {
        if (a == null) return b;
        if (b == null) return a;

        Node result;
        if (a.value.compareTo(b.value) > 0) {
            result = a;
            result.next = sortedMerge(a.next, b);
        } else {
            result = b;
            result.next = sortedMerge(a, b.next);
        }
        return result;
    }

    // Helper function to find the middle node using fast & slow pointers
    private static Node getMiddle(Node head) {
        if (head == null) return head;

        Node slow = head;
        Node fast = head;

        // Move fast by two steps and slow by one step
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    @EqualsAndHashCode
    @ToString
    static class Node<T extends Comparable<T>> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }



}
