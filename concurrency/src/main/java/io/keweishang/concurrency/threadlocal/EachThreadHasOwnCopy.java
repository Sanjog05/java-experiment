package io.keweishang.concurrency.threadlocal;

public class EachThreadHasOwnCopy {
    public static void main(String[] args) {
        final ThreadLocal<Integer> perThreadCounter = new ThreadLocal();

        Thread t1 = new Thread(() -> {
            perThreadCounter.set(0);
            while (perThreadCounter.get() < 1000) {
                System.out.println(perThreadCounter.get());
                perThreadCounter.set(perThreadCounter.get() + 1);
            }
        });

        Thread t2 = new Thread(() -> {
            perThreadCounter.set(new Integer(0));
            while (perThreadCounter.get() < 1000) {
                System.out.println(perThreadCounter.get());
                perThreadCounter.set(perThreadCounter.get() + 1);
            }
        });

        // both threads will count from 0 to 999, each thread owns its own counter, no race condition
        t1.start();
        t2.start();
    }
}
