package io.keweishang.concurrency.threadlocal;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EachThreadHasOwnCopy {
    public static void main(String[] args) {
        final ThreadLocal<Integer> perThreadCounter = new ThreadLocal();

        Thread t1 = new Thread(() -> {
            Calendar c = CalendarFactory.getFactory().getCalendar();
            System.out.println(c.getTimeZone().getDisplayName());
        });

        Thread t2 = new Thread(() -> {
            Calendar c = CalendarFactory.getFactory().getCalendar();
            System.out.println(c.getTimeZone().getDisplayName());
        });

        // Both thread initialize and use their own value
        t1.start();
        t2.start();
    }

    static public class CalendarFactory {
        private ThreadLocal<Calendar> calendarRef = new ThreadLocal<Calendar>() {
            protected Calendar initialValue() {
                System.out.printf("Thread %s is initializing value.\n", Thread.currentThread().getName());
                return new GregorianCalendar();
            }
        };
        private static CalendarFactory instance = new CalendarFactory();

        public static CalendarFactory getFactory() { return instance; }

        public Calendar getCalendar() {
            return calendarRef.get();
        }

        // Don't let outsiders create new factories directly
        private CalendarFactory() {}
    }
}
