package io.keweishang.concurrency.executorservice;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kshang on 29/06/2017.
 */
public class ThreadKilledByException {

  public static void main(String[] args) {
    // when a thread is killed by a runtime exception, the pool creates a new thread so that the number of threads
    // in the pool stays 3.
    ExecutorService es = Executors.newFixedThreadPool(3);
    Runnable simpleTask = getTask();
    for (int i = 0; i < 10; i++) {
      es.execute(simpleTask);
    }
  }

  private static Runnable getTask() {
    return new Runnable() {
      @Override
      public void run() {
        System.out.printf("%s is still alive.\n", Thread.currentThread().getName());
        int random = new Random().nextInt(2);
        if (random == 0) {
          throw new RuntimeException("Unlukey");
        }
      }
    };
  }
}
