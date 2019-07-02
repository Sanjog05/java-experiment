package io.keweishang.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// A thread reacquire the lock when already holding the lock
public class RenentrantExample {
    private static class SharedResource {
        private int value;
        private Lock lck = new ReentrantLock();

        private void addOne() {
            lck.lock();
            try {
                // calling read() will reacquire the lock. If the lock were "non-renentrant", the thread would have
                // been blocked
                value = read() + 1;
            } finally {
                lck.unlock();
            }
        }

        private int read() {
            lck.lock();
            try {
                return value;
            } finally {
                lck.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SharedResource resource = new SharedResource();

        ExecutorService threads = Executors.newFixedThreadPool(1);

        threads.execute(() -> resource.addOne());

        // Tell threads to finish off.
        threads.shutdown();

        // Wait for everything to finish.
        while (!threads.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Awaiting completion of threads.");
        }

        System.out.println(resource.read());
    }
}
