package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Custom ThreadPoolExecutor implementation built from scratch.
 * Supports task execution, thread lifecycle management, and graceful shutdown.
 *
 * @author Nitin Kumar Gupta
 */
public class CustomThreadPoolExecutorL2 {

    private final BlockingQueue<Runnable> taskQueue;
    private final WorkerThread[] workerThreads;
    private volatile boolean isShutdown = false;

    public CustomThreadPoolExecutorL2(int noOfThreads) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workerThreads = new WorkerThread[noOfThreads];

        for (int i = 0; i < noOfThreads; i++) {
            workerThreads[i] = new WorkerThread("CustomPool-Worker-" + (i + 1));
            workerThreads[i].start();
        }
    }

    /**
     * Submits a new task to the queue for execution.
     */
    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is shut down. Cannot accept new tasks.");
        }
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Initiates a graceful shutdown of the thread pool.
     */
    public void shutdown() {
        this.isShutdown = true;
        for (WorkerThread worker : workerThreads) {
            worker.interrupt(); // Interrupt worker threads waiting on taskQueue.take()
        }
    }

    /**
     * Worker Thread inner class responsible for processing queued tasks.
     */
    private class WorkerThread extends Thread {

        public WorkerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            // Keep pulling tasks until shutdown flag is true and queue is empty
            while (!isShutdown || !taskQueue.isEmpty()) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    // Re-check exit condition when interrupted during shutdown
                    if (isShutdown) {
                        break;
                    }
                } catch (Throwable t) {
                    // Catch task exceptions so worker thread survives
                    System.err.println(getName() + " encountered an error executing task: " + t.getMessage());
                }
            }
        }
    }

    // Driver / Demo Execution
    public static void main(String[] args) throws InterruptedException {
        CustomThreadPoolExecutorL2 pool = new CustomThreadPoolExecutorL2(3);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " processing task " + taskId);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ignored) {}
            });
        }

        Thread.sleep(1000);
        System.out.println("Initiating pool shutdown...");
        pool.shutdown();
    }
}