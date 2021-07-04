package io.keweishang.pinot;

import org.apache.pinot.client.*;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Test query latency under different QPS
 */
public class PerformanceTestRunner {
  public static void main(String[] args) {
    final BlockingQueue<String> queryQueue = new LinkedBlockingQueue<>();
    final BlockingQueue<Long> latencyQueue = new LinkedBlockingQueue<>();

    final int numOfQueries = 10000;
    final int numOfQueryWorkers = 6;
    final int ApproxThroughput = 100;
    final String zkUrl = "localhost:2181";
    final String pinotClusterName = "PinotCluster";

    final CountDownLatch latch = new CountDownLatch(numOfQueries);
    addQueries(queryQueue, numOfQueries, ApproxThroughput);
    processQueries(queryQueue, latencyQueue, numOfQueryWorkers, latch, zkUrl, pinotClusterName);
    printStats(latencyQueue, latch);
  }

  private static void processQueries(
      BlockingQueue<String> queryQueue,
      BlockingQueue<Long> latencyQueue,
      int numOfQueryWorkers,
      CountDownLatch latch,
      String zkUrl,
      String pinotClusterName) {
    ExecutorService queryPool = Executors.newFixedThreadPool(numOfQueryWorkers);
    for (int i = 0; i < numOfQueryWorkers; i++) {
      queryPool.submit(
          () -> {
            Connection pinotConnection =
                ConnectionFactory.fromZookeeper(zkUrl + "/" + pinotClusterName);

            while (true) {
              try {
                String query = queryQueue.take();
                Request pinotClientRequest = new Request("sql", query);
                long beginTimeStamp = System.currentTimeMillis();
                queryDB(pinotConnection, pinotClientRequest);
                latencyQueue.add(System.currentTimeMillis() - beginTimeStamp);
                latch.countDown();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
    }
  }

  private static boolean queryDB(Connection pinotConnection, Request pinotClientRequest) {
    ResultSetGroup pinotResultSetGroup = pinotConnection.execute(pinotClientRequest);
    ResultSet resultTableResultSet = pinotResultSetGroup.getResultSet(0);

    String columnValue = resultTableResultSet.getString(0, 0);
    String columnName = resultTableResultSet.getColumnName(0);

    System.out.println("ColumnName: " + columnName + ", ColumnValue: " + columnValue);
    return true;
  }

  private static void addQueries(
      BlockingQueue<String> queryQueue, int numOfQueries, int throughput) {
    Integer[] provider_ids = {
      9626, 9624, 1545, 3727, 18077, 9627, 11093, 1546, 1616, 137, 11466, 2957, 9631, 99, 1415,
      1576, 9664, 8836, 20670, 8800, 1578, 1611, 1663, 1414, 23122, 4168, 7065, 13929, 9099, 7362,
      11083, 9102, 9670, 84, 1553, 4092, 6435, 2543, 2560, 1128, 5166, 9391, 3505, 8280, 7044, 1337,
      2010, 240, 9145, 19013, 4475, 3475, 1029, 4026, 339, 1571, 597, 1124, 12938, 12588, 17939,
      9306, 1516, 13311, 2853, 880, 8886, 2897, 12917, 8974, 19522, 2860, 3718, 599, 18005, 8096,
      1570, 596, 2910, 361, 4228, 6346, 760, 7045, 7048, 15871, 16656, 1, 4344, 16727, 11566, 12180,
      1535, 8442, 8228, 7515, 474, 14974, 20043, 2924
    };
    ExecutorService queryPool = Executors.newFixedThreadPool(1);
    Random random = new Random();
    queryPool.submit(
        () -> {
          for (int i = 0; i < numOfQueries; i++) {
            queryQueue.add(
                String.format(
                    "SELECT COUNT(*) as cnt FROM delivery_order_v5 where provider_id = %d",
                    provider_ids[random.nextInt(provider_ids.length)]));
            try {
              Thread.sleep(1000 / throughput);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        });
  }

  private static void printStats(BlockingQueue<Long> latencyQueue, CountDownLatch latch) {
    long startTimeStamp = System.currentTimeMillis();
    try {
      latch.await();
      int numOfQueries = latencyQueue.size();
      long totalLatency = 0;
      System.out.println(String.format("Number of queries executed: %d", numOfQueries));
      while (!latencyQueue.isEmpty()) {
        totalLatency += latencyQueue.take();
      }
      System.out.println(
          String.format("Latency (ms/query): %f", (double) totalLatency / numOfQueries));
      System.out.println(
          String.format(
              "Throughput (queries/sec): %d",
              numOfQueries * 1000 / (System.currentTimeMillis() - startTimeStamp)));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
