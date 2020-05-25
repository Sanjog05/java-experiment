package io.keweishang.grpc.hellohost.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.keweishang.grpc.helloworld.GreeterGrpc;
import io.keweishang.grpc.helloworld.HelloReply;
import io.keweishang.grpc.helloworld.HelloRequest;

public class GrpcClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();
        GreeterGrpc.GreeterBlockingStub stub = GreeterGrpc.newBlockingStub(channel);
        HelloReply helloReply = stub.sayHello(HelloRequest.newBuilder().setName("Kewei").build());
        System.out.println("Reply received from server:\n" + helloReply);
        channel.shutdown();
    }
}
