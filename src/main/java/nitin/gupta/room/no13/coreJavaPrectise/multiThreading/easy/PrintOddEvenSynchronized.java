package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.easy;

public class PrintOddEvenSynchronized {
    private final int limit;
    private int counter = 1;

    public PrintOddEvenSynchronized(int limit) {
        this.limit = limit;
    }

    public synchronized void printNumber(boolean isEven) {
        while (counter <= limit) {
            while ((counter % 2 == 0) != isEven) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (counter <= limit) {
                System.out.print(counter + " ");
                counter++;
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        PrintOddEvenSynchronized printer = new PrintOddEvenSynchronized(20);

        Thread oddThread = new Thread(() -> printer.printNumber(false), "Odd");
        Thread evenThread = new Thread(() -> printer.printNumber(true), "Even");

        oddThread.start();
        evenThread.start();
    }
}