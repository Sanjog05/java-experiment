package io.keweishang.exception;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadStackSizeExample {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new InfiniteRecursion());
        es.execute(new HeartBeat());
    }

    // The thread that runs this task will throw java.lang.StackOverflowError.
    // However, this does not kill the JVM. Only the thread is affected.
    static class InfiniteRecursion implements Runnable {
        @Override
        public void run() {
            recursiveFn(0);
        }
        private void recursiveFn(int i) {
            recursiveFn(i + 1);
        }
    }

    // The thread that runs this task is not affected by another thread's
    // java.lang.StackOverflowError
    static class HeartBeat implements Runnable {
        @Override
        public void run() {
            while(true) {
                System.out.println("heart beat");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
