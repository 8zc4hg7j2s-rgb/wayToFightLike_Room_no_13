package nitin.gupta.room.no13.coreJavaPrectise.multiThreading.hard;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * OLd Implementation
 * <br>
 * public int fib(int n) {<br>
 * if (n <= 1) //Base Condition<br>
 * return n;<br>
 * else { //Recursive case<br>
 * return fib(n - 1) + fib(n - 2);<br>
 * }<br>
 * }<br>
 */

public class Fibonacci extends RecursiveTask<Integer> {
    private final int n;

    Fibonacci(int n) {
        this.n = n;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Fibonacci fibonacci = new Fibonacci(10);
        //  Integer aaaa = ForkJoinPool.commonPool().invoke(fibonacci);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        forkJoinPool.invoke(fibonacci);
        //System.out.println("aaaa"+ aaaa);
        System.out.println(fibonacci.get());
    }

    @Override
    protected Integer compute() {
        if (n <= 1)
            return n;
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        return f2.compute() + f1.join();
    }
}
