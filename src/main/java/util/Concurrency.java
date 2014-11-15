package util;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author mohammo on 11/15/2014.
 */
public class Concurrency {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        final int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<?>> futures = new LinkedList<Future<?>>();

        for (int i = 0; i < 10; i++) {
            final Future<?> future = executorService.submit(new Runnable() {
                @Override
                public void run() {
                    new ComplexTask().doSomething();
                }
            });

//            future.get();
            futures.add(future);
        }

        System.out.println("Waiting for child threads to complete");
        for (Future<?> future : futures) {
            future.get(); //waits for the given thread result
        }

        System.out.println("All child threads completed...");
        System.out.println("time taken (seconds): " + (System.currentTimeMillis() - start)/1000L);
    }

    /**
     *
     */
    public static class ComplexTask {
        public void doSomething(){
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
