package io.keweishang.concurrency.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// when using Lock interface, we need to add lock.unlock() in the finally clause, to guarantee that we will not hold
// the lock forever in case of exception
public class TryFinallyExample {

    private static class SharedResource {
        private int value;
        private Lock lck = new ReentrantLock();

        private void addOne() {
            lck.lock();
            try {
                // processing resource may throw Exception
                value++;
            } finally {
                lck.unlock();
            }
        }

        private int read() {
            // we also need to acquire lock when reading the shared resource.
            // Otherwise, we would have visibility problem: change made to value by thread A is not guaranteed to
            // be visible by thread B. We need to acquire the lock to apply the happened-before rule.
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

        ExecutorService threads = Executors.newFixedThreadPool(2);

        int tasks = 2;
        for (int task = 0; task < tasks; task++) {
            threads.execute(() -> {
                for (int i = 0; i < 1_000_000; i++) {
                    resource.addOne();
                }
            });
        }

        // Tell threads to finish off.
        threads.shutdown();

        // Wait for everything to finish.
        while (!threads.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("Awaiting completion of threads.");
        }

        System.out.println(resource.read());
    }
}
