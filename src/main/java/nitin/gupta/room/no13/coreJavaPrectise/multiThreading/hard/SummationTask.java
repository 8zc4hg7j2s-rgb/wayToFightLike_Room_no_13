package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Example Program that sums all elements of an Array
 */
public class SummationTask extends RecursiveTask<Long> {
    final long[] array;
    final int lo, hi;
    private final int THRESHOLD = 2;

    SummationTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int a = 25;
        int b = a >>> 1;
        System.out.println(b);
        long[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        SummationTask incrementTask = new SummationTask(array, 0, 10);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(incrementTask);
        System.out.println(incrementTask.get());
    }

    @Override
    protected Long compute() {
        if (hi - lo < THRESHOLD) {
            long result = 0;
            for (int i = lo; i < hi; ++i)
                result += array[i];
            return result;
        } else {
            int mid = (lo + hi) >>> 1;
            // int mid = (lo + hi) / 2;
            SummationTask left = new SummationTask(array, lo, mid);
            left.fork();
            SummationTask right = new SummationTask(array, mid, hi);
            long result = right.compute() + left.join();
            return result;
        }
    }
}
