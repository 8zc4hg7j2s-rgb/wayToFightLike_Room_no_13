package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class IncrementTask extends RecursiveAction {
    final long[] array;
    final int lo, hi;
    private final int THRESHOLD = 100;

    IncrementTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        IncrementTask incrementTask = new IncrementTask(array, 0, 9);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(incrementTask);
        System.out.println(incrementTask.get());
    }

    protected void compute() {
        if (hi - lo < THRESHOLD) {
            for (int i = lo; i < hi; ++i)
                array[i]++;
        } else {
            int mid = (lo + hi) >>> 1;
            invokeAll(new IncrementTask(array, lo, mid), new IncrementTask(array, mid, hi));
        }
    }
}
