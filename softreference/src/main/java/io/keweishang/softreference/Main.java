package io.keweishang.softreference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        softIsNotGCedUntilOOM();
        weakIsGCedEveryGC();
    }

    private static void softIsNotGCedUntilOOM() {
        System.out.println("Testing softIsNotGCedUntilOOM...");
        ReferenceQueue<Date> queue = new ReferenceQueue();

        Date a = new Date();
        Reference<Date> ref = new SoftReference<>(a, queue);
        a = null;
        System.gc();
        System.out.println("Object = " + ref.get());
        System.out.println("Soft reference dequeued = " + queue.poll());
    }

    private static void weakIsGCedEveryGC() {
        System.out.println("Testing weakIsGCedEveryGC...");
        ReferenceQueue<Date> queue = new ReferenceQueue();

        Date a = new Date();
        Reference<Date> ref = new WeakReference<>(a, queue);
        a = null;
        System.gc();
        System.out.println("Object = " + ref.get());
        System.out.println("Weak reference dequeued = " + queue.poll());

        // queue.poll().get() returns null
        // cannot resurrect the object once the soft/weak reference is enqueued
    }
}
