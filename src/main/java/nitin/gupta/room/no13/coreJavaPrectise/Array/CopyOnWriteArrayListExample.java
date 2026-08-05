package nitin.gupta.room.no13.coreJavaPrectise.Array;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        CopyOnWriteArrayList<Integer> numbers
                = new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);
        List<Integer> result2 = new LinkedList<>();
        result2.add(20);
        iterator.forEachRemaining(result2::add);
        while (iterator.hasNext()) {
            numbers.add(10);
            System.out.println(iterator.next());
        }
    }

    static void main11() {

    List<String> numList = new CopyOnWriteArrayList<>();
        numList.add("1");
        numList.add("2");
        numList.add("3");
        numList.add("4");

    //This thread will iterate the list
    Thread thread1 = new Thread(){
        public void run(){
            try{
                Iterator<String> i = numList.iterator();
                while (i.hasNext()){
                    System.out.println(i.next());
                    // Using sleep to simulate concurrency
                    Thread.sleep(1000);
                    if(i.equals("2"))
                        i.remove();
                }
            }catch(ConcurrentModificationException | InterruptedException e){
                System.out.println("thread1 : Concurrent modification detected       on this list");
                e.printStackTrace();
            }
        }
    };
        thread1.start();

    // This thread will try to add to the collection,
    // while the collection is iterated by another thread.
    Thread thread2 = new Thread(){
        public void run(){
            try{
                // Using sleep to simulate concurrency
                Thread.sleep(2000);
                // adding new value to the shared list
                numList.add("5");
                // numList.remove("5");
                System.out.println("new value added to the list");
            }catch(ConcurrentModificationException | InterruptedException e){
                System.out.println("thread2 : Concurrent modification detected       on the List");
            }
        }
    };
        thread2.start();
}
}
