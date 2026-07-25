package list;

import nitin.gupta.room.no13.datastructure.list.DoublyLinkedList;

public class TestingDoublyLinkedList {
    static void main() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.insertAtHead(1);
        list.insertAtHead(2);
        list.insertAtHead(3);
        list.insertAtHead(44);
        list.insertAtTail(5);
        list.insertAtTail(6);
        list.insertAtTail(7);
        list.insertAtPosition(9,7);
        list.insertAtPosition(8,8);
        list.insertBefore(list.getHead(),1 );
        list.insertBefore(list.getTail(),8 );
        list.insertBefore(list.getMiddle(),4 );
        list.insertAfter(list.getHead(), 11);
        list.insertAfter(list.getTail(), 12);
        list.insertAfter(list.getMiddle(), 13);
        list.print();
        list.printReverse();
        list.deleteAtPosition(0);
        list.print();
        list.printReverse();
        list.deleteAtPosition(16);
        list.print();
        list.printReverse();
    }
}
