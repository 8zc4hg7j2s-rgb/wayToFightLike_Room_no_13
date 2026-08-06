package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Custom ThreadPoolExecutor supporting pause and resume capabilities.
 *
 * @author Nitin Kumar Gupta
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    private volatile boolean isPaused;
    private final ReentrantLock pauseLock = new ReentrantLock();
    private final Condition unpaused = pauseLock.newCondition();

    public CustomThreadPoolExecutor(int corePoolSize,
                                    int maximumPoolSize,
                                    long keepAliveTime,
                                    TimeUnit unit,
                                    BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * Pauses execution of incoming tasks.
     * Running tasks will finish, but queued tasks will wait before starting execution.
     */
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * Resumes task execution and signals all waiting worker threads.
     */
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * Hook method called prior to executing any Runnable in the given thread.
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException ie) {
            // Restore interrupt flag for worker thread
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * Returns true if the executor is currently paused.
     */
    public boolean isPaused() {
        return isPaused;
    }
}