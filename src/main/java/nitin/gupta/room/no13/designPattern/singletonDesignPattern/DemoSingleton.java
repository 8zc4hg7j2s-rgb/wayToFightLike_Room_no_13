package nitin.gupta.room.no13.designPattern.singletonDesignPattern;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class DemoSingleton implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int count;

    // Private constructor with reflection defense
    private DemoSingleton() {
        if (DemoSingletonHolder.INSTANCE != null) {
            throw new IllegalStateException("Instance already created! Use getInstance() method.");
        }
        this.count = 1 + ThreadLocalRandom.current().nextInt(1000);
    }

    public static DemoSingleton getInstance() {
        return DemoSingletonHolder.INSTANCE;
    }

    // Prevents breaking Singleton property during Deserialization
    @Serial
    protected Object readResolve() {
        return getInstance();
    }

    @Override
    public String toString() {
        return "DemoSingleton [count=" + count + ", hash=" + System.identityHashCode(this) + "]";
    }

    // Bill Pugh Singleton Holder
    private static final class DemoSingletonHolder {
        private static final DemoSingleton INSTANCE = new DemoSingleton();
    }
}

class Testing {
    public static void main(String[] args) throws InterruptedException {
        int taskCount = 1000;
        // Efficient thread pool size matching hardware cores rather than spawning 1000 OS threads
        int threads = Math.min(taskCount, Runtime.getRuntime().availableProcessors() * 2);

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        Runnable task = () -> {
            DemoSingleton instance = DemoSingleton.getInstance();
            System.out.println(Thread.currentThread().getName() + " -> " + instance);
        };

        for (int i = 0; i < taskCount; i++) {
            executorService.submit(task);
        }

        // Proper executor termination sequence
        executorService.shutdown();
        if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
            executorService.shutdownNow();
        }
    }
}