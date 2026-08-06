package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.easy;

class PrintSequenceRunnable implements Runnable {
    private final int remainder;
    private final int printLimit;
    private final Object lock;
    private static int number = 1;

    public PrintSequenceRunnable(int remainder, int printLimit, Object lock) {
        this.remainder = remainder;
        this.printLimit = printLimit;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                while (number <= printLimit && number % 3 != remainder) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                // Break loop and wake up sleeping threads when max limit is reached
                if (number > printLimit) {
                    lock.notifyAll();
                    break;
                }

                System.out.println(Thread.currentThread().getName() + " " + number);
                number++;
                lock.notifyAll();
            }
        }
    }
}

public class PrintNumberInSequence {
    public static void main(String[] args) {
        int limit = 10;
        Object lock = new Object();

        // Runnable 1 handles remainder 1 (1, 4, 7...)
        // Runnable 2 handles remainder 2 (2, 5, 8...)
        // Runnable 3 handles remainder 0 (3, 6, 9...)
        Thread t1 = new Thread(new PrintSequenceRunnable(1, limit, lock), "T1");
        Thread t2 = new Thread(new PrintSequenceRunnable(2, limit, lock), "T2");
        Thread t3 = new Thread(new PrintSequenceRunnable(0, limit, lock), "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}