package io.keweishang.grpc.vstream;

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

public class VStreamClient {

    // grpc servers
    static String VTGATE_HOST = "localhost";
    static int VTGATE_PORT = 15991;
    static String VTCTLD_HOST = "localhost";
    static int VTCTLD_PORT = 15999;

    // listening to a specific unsharded keyspace
    static String KEYSPACE = "commerce";
    static String SHARD = "0";

    public static void main(String[] args) {
        System.out.printf("Consuming keyspace: %s/%s\n", KEYSPACE, SHARD);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(VTGATE_HOST, VTGATE_PORT)
                .usePlaintext()
                .build();

        Binlogdata.VGtid vgtid = getPosition(KEYSPACE, SHARD);

        VitessGrpc.VitessBlockingStub stub = VitessGrpc.newBlockingStub(channel);
        Iterator<Vtgate.VStreamResponse> responseIter =
                stub.vStream(newVStreamRequest(vgtid, Topodata.TabletType.MASTER));
        while (responseIter.hasNext()) {
            Vtgate.VStreamResponse response = responseIter.next();
            for (Binlogdata.VEvent event : response.getEventsList()) {
                System.out.println(event);
                /*
                    "
                    type: BEGIN
                    timestamp: 1590419166
                    current_time: 1590419166855909588

                    type: FIELD
                    timestamp: 1590419166
                    field_event {
                      table_name: "commerce.product"
                      fields {
                        name: "sku"
                        type: VARBINARY
                      }
                      fields {
                        name: "description"
                        type: VARBINARY
                      }
                      fields {
                        name: "price"
                        type: INT64
                      }
                    }
                    current_time: 1590419166855972244

                    type: ROW
                    timestamp: 1590419166
                    row_event {
                      table_name: "commerce.product"
                      row_changes {
                        after {
                          lengths: 2
                          lengths: 4
                          lengths: 2
                          values: "s5book10"
                        }
                      }
                    }
                    current_time: 1590419166856004721

                    type: VGTID
                    vgtid {
                      shard_gtids {
                        keyspace: "commerce"
                        shard: "0"
                        gtid: "MySQL56/a790d864-9ba1-11ea-99f6-0242ac11000a:1-1513"
                      }
                    }

                    type: COMMIT
                    timestamp: 1590419166
                    current_time: 1590419166856058983
                    "
                 */
            }
        }

        channel.shutdown();
    }

    private static Vtgate.VStreamRequest newVStreamRequest(Binlogdata.VGtid vgtid, Topodata.TabletType master) {
        return Vtgate.VStreamRequest.newBuilder()
                .setVgtid(vgtid)
                .setTabletType(Topodata.TabletType.MASTER).build();
    }

    public static Binlogdata.VGtid getPosition(String keyspace, String shard) {
        List<String> args = new ArrayList<>();
        args.add("ShardReplicationPositions");
        args.add(keyspace + ":" + shard);
        List<String> results = execVtctl(args, VTCTLD_HOST, VTCTLD_PORT);
        System.out.println("Get the latest replication positions of a specific keyspace/shard:\n" + results);
        /*
            "
            Get the latest replication positions of a specific keyspace/shard:
            [zone1-1564760600 commerce 0 master zone1-commerce-0-replica-0.vttablet:15002 zone1-commerce-0-replica-0.vttablet:3306 [] 2020-05-21T20:28:57Z MySQL56/a790d864-9ba1-11ea-99f6-0242ac11000a:1-1512 0
            , zone1-1564760602 commerce 0 replica zone1-commerce-0-replica-2.vttablet:15002 zone1-commerce-0-replica-2.vttablet:3306 [] <null> MySQL56/a790d864-9ba1-11ea-99f6-0242ac11000a:1-1512 0
            , zone1-1564760601 commerce 0 replica zone1-commerce-0-replica-1.vttablet:15002 zone1-commerce-0-replica-1.vttablet:3306 [] <null> MySQL56/a790d864-9ba1-11ea-99f6-0242ac11000a:1-1512 0
            ]
            "
         */


        String shardGtid = results.get(0).split(" ")[8];
        System.out.println("Starting from ShardGtid: " + shardGtid); // "Starting from ShardGtid: MySQL56/a790d864-9ba1-11ea-99f6-0242ac11000a:1-1512"
        return Binlogdata.VGtid.newBuilder()
                .addShardGtids(
                        Binlogdata.ShardGtid.newBuilder()
                                .setKeyspace(keyspace)
                                .setShard(shard)
                                .setGtid(shardGtid)
                                .build()
                ).build();
    }

    public static List<String> execVtctl(List<String> args, String vtctldHost, int vtctldPort) {
        List<String> res = new ArrayList<>();

        ManagedChannel channel = ManagedChannelBuilder.forAddress(vtctldHost, vtctldPort)
                .usePlaintext()
                .build();

        VtctlGrpc.VtctlBlockingStub stub = VtctlGrpc.newBlockingStub(channel);
        Iterator<Vtctldata.ExecuteVtctlCommandResponse> responseIter = stub.executeVtctlCommand(
                Vtctldata.ExecuteVtctlCommandRequest.newBuilder()
                        .setActionTimeout(10_000_000_000L) // 10 seconds in nano-seconds
                        .addArgs(args.get(0))
                        .addArgs(args.get(1))
                        .build()
        );

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
