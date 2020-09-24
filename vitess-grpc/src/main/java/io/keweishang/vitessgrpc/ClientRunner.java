package io.keweishang.vitessgrpc;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/** Vitess gRPC client runner for debugging purpose. */
public class ClientRunner {
  private static final Logger LOGGER = Logger.getLogger(ClientRunner.class.getName());

  // args: keyspace shards gtid_index
  // commerce 0 8 localhost
  // commerce - 7 localhost
  // customer -80,80- 7 localhost
  public static void main(String[] args) throws InterruptedException {
    String keyspace = args[0];
    List<String> shards = Arrays.asList(args[1].split(","));
    int gtidIdx = Integer.valueOf(args[2]);
    String host = args[3];
    LOGGER.info("keyspace: " + keyspace);
    LOGGER.info("shards: " + shards);
    LOGGER.info("gtidIdx: " + gtidIdx);
    LOGGER.info("host: " + host);

    BlockingVStreamClient client = new BlockingVStreamClient(keyspace, shards, gtidIdx, host);
    client.startStreaming();
  }
}
