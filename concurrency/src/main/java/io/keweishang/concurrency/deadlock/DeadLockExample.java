package io.keweishang.concurrency.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeadLockExample {

    public static void main(String[] args) throws InterruptedException {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Runnable lock1lock2 = () -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                synchronized (lock1) {
                    System.out.println("lock1lock2 has acquired lock1");
                    synchronized (lock2) {
                        System.out.println("lock1lock2 has acquired lock2");
                    }
                }
            }

        };

        Runnable lock2lock1 = () -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                synchronized (lock2) {
                    System.out.println("lock2lock1 has acquired lock2");
                    synchronized (lock1) {
                        System.out.println("lock2lock1 has acquired lock1");
                    }
                }
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(lock1lock2);
        es.execute(lock2lock1);
        System.out.println("Waiting for termination for 1 minute...");
        es.awaitTermination(1, TimeUnit.MINUTES);
    }


}
