package list;

import nitin.gupta.room.no13.datastructure.list.DoublyLinkedList;

public class TestingDoublyLinkedList {
    static void main() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertAtHead(1);
        list.insertAtHead(2);
        list.insertAtHead(3);
        list.insertAtHead(4);
        list.print();
        list.printReverse();
        list.insertAtTail(5);
        list.insertAtTail(6);
        list.insertAtTail(7);
        list.print();
        list.printReverse();
        list.insertAtPosition(8,8);
        list.insertAtPosition(9,7);
        list.print();
        list.printReverse();
    }
}
