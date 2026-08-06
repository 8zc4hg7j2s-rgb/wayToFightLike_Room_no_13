package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeSpread {
    final AtomicBoolean started = new AtomicBoolean(false);
    final AtomicInteger stopCounter;
    long startTime;
    long stopTime;

    public TimeSpread(int threads) {
        stopCounter = new AtomicInteger(threads);
    }

    public static long time(Executor executor, int concurrency, final Runnable action) throws InterruptedException {
        final CountDownLatch ready = new CountDownLatch(concurrency);
        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch done = new CountDownLatch(concurrency);
        for (int i = 0; i < concurrency; i++) {
            executor.execute(new Runnable() {
                public void run() {
                    ready.countDown(); // Tell timer we're ready
                    try {
                        start.await(); // Wait till peers are ready
                        action.run();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown(); // Tell timer we're done
                    }
                }
            });
        }
        ready.await(); // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await(); // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }

    public static void main(String[] args) throws InterruptedException {
        int threads = 300;
        final TimeSpread timeSpread = new TimeSpread(threads);
        Runnable t = new Runnable() {
            public void run() {
                timeSpread.start();
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                }
                timeSpread.stop();
            }
        };
        List<Thread> list = new ArrayList<>(threads);
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread(t);
            thread.start();
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.join();
        }
        System.out.println("time spread = " + timeSpread.timeConsumed());
    }

    public void start() {
        if (!started.get()) {
            if (started.compareAndSet(false, true)) {
                startTime = System.currentTimeMillis();
            }
        }
    }

    public void stop() {
        if (stopCounter.getAndDecrement() == 1) {
            stopTime = System.currentTimeMillis();
        }
    }

    public long timeConsumed() {
        return stopTime - startTime;
    }
}
