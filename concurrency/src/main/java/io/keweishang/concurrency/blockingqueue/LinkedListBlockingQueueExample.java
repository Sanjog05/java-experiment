package io.keweishang.concurrency.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LinkedListBlockingQueueExample {

    public static void main(String[] args) throws InterruptedException {
//        boundedWithCapacity();
        unboundedWithoutCapacity();
    }

    private static void unboundedWithoutCapacity() throws InterruptedException {
        // capacity not specified, by default its Integer.MAX_VALUE
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        System.out.println("put 1st item");
        queue.put(10);

        System.out.println("put 2nd item");
        queue.put(20);

        System.out.println("terminated");
    }

    private static void boundedWithCapacity() throws InterruptedException {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);

        System.out.println("put 1st item");
        queue.put(10);

        System.out.println("put 2nd item");
        queue.put(20);

        // not reachable, because queue is over-capacity
        System.out.println("terminated");
    }
}
