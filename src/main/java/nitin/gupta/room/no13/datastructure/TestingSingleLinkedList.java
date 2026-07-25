package nitin.gupta.room.no13.datastructure;

import java.util.ArrayList;
import java.util.List;

public class TestingSingleLinkedList {
    static void main() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        SingleLinkedList<Integer> listSSL = new SingleLinkedList<>();
        listSSL.addFirst(10);
        listSSL.addFirst(20);
        listSSL.addFirst(30);
        listSSL.addLast(100);
        listSSL.addLast(110);
        listSSL.addLast(120);
        listSSL.addAtPosition(0,33);
        listSSL.addAtPosition(4,44);
        listSSL.addAtPosition(9,55);
        listSSL.addFirst(10);
        listSSL.addFirst(20);
        listSSL.addFirst(30);
        listSSL.addLast(100);
        listSSL.addLast(110);
        listSSL.addLast(120);
        listSSL.addAtPosition(0,33);
        listSSL.addAtPosition(4,44);
        listSSL.addAtPosition(9,55);
        listSSL.print();
        listSSL.removeDuplicate();
        listSSL.print();
        listSSL.revers();
        listSSL.print();
       

    }
}
