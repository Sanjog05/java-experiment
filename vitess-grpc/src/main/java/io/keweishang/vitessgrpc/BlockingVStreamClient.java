package io.keweishang.vitessgrpc;

import binlogdata.Binlogdata;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.vitess.proto.Topodata;
import io.vitess.proto.Vtgate;
import io.vitess.proto.grpc.VitessGrpc;
import logutil.Logutil;
import vtctldata.Vtctldata;
import vtctlservice.VtctlGrpc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockingVStreamClient {
  private static final Logger LOGGER = Logger.getLogger(BlockingVStreamClient.class.getName());

  // vtgate grpc server
  private static int VTGATE_PORT = 15991;

  // vtctld grpc server
  private static int VTCTLD_PORT = 15999;

  private final String keyspace;
  private final List<String> shards;
  private final int gtidIdx;
  private final String host;

  protected final ManagedChannel channel;

  public BlockingVStreamClient(String keyspace, List<String> shards, int gtidIdx, String host) {
    this.keyspace = keyspace;
    this.shards = shards;
    this.gtidIdx = gtidIdx;
    this.host = host;
    this.channel = newChannel();
  }

  protected void closeAndWait() throws InterruptedException {
    channel.shutdownNow();
    LOGGER.info("Channel shutdownNow is invoked.");
    if (channel.awaitTermination(5, TimeUnit.SECONDS)) {
      LOGGER.info("Channel is shut down in time. Exiting.");
    } else {
      LOGGER.warning("Give up waiting for channel shutdownNow. Exiting.");
    }
  }

  public void startStreaming() throws InterruptedException {
    try {
      Binlogdata.VGtid vgtid = getPosition();

      VitessGrpc.VitessBlockingStub sub = VitessGrpc.newBlockingStub(newChannel());
      while (true) {
        Iterator<Vtgate.VStreamResponse> response =
            sub.vStream(newVStreamRequest(vgtid, Topodata.TabletType.MASTER));
        while (response.hasNext()) {
          processResponse(response.next());
        }
      }
    } finally {
      closeAndWait();
    }
  }

  protected Binlogdata.VGtid processResponse(Vtgate.VStreamResponse response) {
    Binlogdata.VGtid vGtid = null;
    LOGGER.info("======response======");
    for (Binlogdata.VEvent event : response.getEventsList()) {
      if (event.getVgtid().getShardGtidsCount() != 0) {
        vGtid = event.getVgtid();
      }
      LOGGER.info(event.toString());
    }

    return vGtid;
  }

  protected static Vtgate.VStreamRequest newVStreamRequest(
      Binlogdata.VGtid vgtid, Topodata.TabletType tabletType) {
    return Vtgate.VStreamRequest.newBuilder().setVgtid(vgtid).setTabletType(tabletType).build();
  }

  protected ManagedChannel newChannel() {
    return ManagedChannelBuilder.forAddress(host, VTGATE_PORT).usePlaintext().build();
  }

  // Get current vgtid position of a specific keyspace/shard
  protected Binlogdata.VGtid getPosition() {
    Binlogdata.VGtid.Builder builder = Binlogdata.VGtid.newBuilder();
    for (String shard : shards) {
      builder.addShardGtids(getShardGtid(shard));
    }
    return builder.build();
  }

  protected Binlogdata.ShardGtid getShardGtid(String shard) {
    List<String> args = new ArrayList<>();
    args.add("ShardReplicationPositions");
    args.add(keyspace + ":" + shard);
    List<String> results = execVtctl(args, host, VTCTLD_PORT);

    // String shardGtid = "MariaDB/0-1591716567-305";
    String shardGtid = results.get(0).split(" ")[gtidIdx];
    LOGGER.log(Level.INFO, "ShardGtid: {0}", shardGtid);
    return Binlogdata.ShardGtid.newBuilder()
        .setKeyspace(keyspace)
        .setShard(shard)
        .setGtid(shardGtid)
        .build();
  }

  private static List<String> execVtctl(List<String> args, String vtctldHost, int vtctldPort) {
    List<String> res = new ArrayList<>();

    ManagedChannel channel =
        ManagedChannelBuilder.forAddress(vtctldHost, vtctldPort).usePlaintext().build();

    VtctlGrpc.VtctlBlockingStub stub = VtctlGrpc.newBlockingStub(channel);
    Iterator<Vtctldata.ExecuteVtctlCommandResponse> responseIter =
        stub.executeVtctlCommand(
            Vtctldata.ExecuteVtctlCommandRequest.newBuilder()
                .setActionTimeout(10_000_000_000L) // 10 seconds in nano-seconds
                .addArgs(args.get(0))
                .addArgs(args.get(1))
                .build());

    while (responseIter.hasNext()) {
      Vtctldata.ExecuteVtctlCommandResponse response = responseIter.next();
      Logutil.Event event = response.getEvent();
      if (Logutil.Level.CONSOLE.equals(event.getLevel())) {
        res.add(event.getValue());
      }
    }

    channel.shutdown();
    return res;
  }
}
