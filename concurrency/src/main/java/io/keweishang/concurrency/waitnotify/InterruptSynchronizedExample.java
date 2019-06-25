package io.keweishang.concurrency.waitnotify;

// Interrupt the thread when it is trying to obtain the implicit lock by using synchronized()
public class InterruptSynchronizedExample {

    public static void main(String[] args) {
        Object lock = new Object();
        Thread t1 = new Thread(new ObtainAndSleepTask(lock));
        t1.start();
        Thread t2 = new Thread(new ObtainAndSleepTask(lock));
        t2.start();

        try {
            // wait a bit for both threads to start
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("main thread sleep is interrupted");
            e.printStackTrace();
        }

        // interrupt t2 while t2 is trying to entry synchronized()
        // Conclusion: we cannot interrupt() the thread when the thread is trying to obtain the implicit lock with
        // synchronized(). However, when the thread being interrupted obtains the lock, its thread flat is set to
        // "interrupted", calling any function with throws InterruptedException signature would actually actually
        // throw InterruptedException
        t2.interrupt();

        try {
            System.out.printf("Waiting for %s to finish...\n", t1.getName());
            t1.join();
            System.out.printf("%s finished...\n", t1.getName());

            System.out.printf("Waiting for %s to finish...\n", t2.getName());
            t2.join();
            System.out.printf("%s finished...\n", t2.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ObtainAndSleepTask implements Runnable {

        private Object lock;

        public ObtainAndSleepTask(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.printf("%s is trying to obtain the lock...\n", Thread.currentThread().getName());
            synchronized (lock) {
                int seconds = 5;
                System.out.printf("%s obtained the lock! Sleep for %d seconds...\n", Thread.currentThread().getName(), seconds);
                try {
                    Thread.sleep(1000 * seconds);
                } catch (InterruptedException e) {
                    System.out.printf("%s sleep is interrupted\n", Thread.currentThread().getName());
                    e.printStackTrace();
                }
            }
            System.out.printf("%s released the lock!\n", Thread.currentThread().getName());
        }
    }
}
