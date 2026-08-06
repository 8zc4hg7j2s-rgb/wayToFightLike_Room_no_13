package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.easy;

import java.util.concurrent.atomic.AtomicInteger;

public class SequencePrinterAtomic {
    private final int totalThreads;
    private final int maxLimit;
    private final AtomicInteger state = new AtomicInteger(1);

    public SequencePrinterAtomic(int totalThreads, int maxLimit) {
        this.totalThreads = totalThreads;
        this.maxLimit = maxLimit;
    }

    public void printSequence(int threadId) {
        while (state.get() <= maxLimit) {
            if (state.get() % totalThreads == threadId % totalThreads) {
                System.out.println(Thread.currentThread().getName() + " " + state.get());
                state.incrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        int limit = 10;
        SequencePrinterAtomic printer = new SequencePrinterAtomic(3, limit);

        Thread t1 = new Thread(() -> printer.printSequence(1), "T1");
        Thread t2 = new Thread(() -> printer.printSequence(2), "T2");
        Thread t3 = new Thread(() -> printer.printSequence(3), "T3");

        t1.start();
        t2.start();
        t3.start();
    }
}