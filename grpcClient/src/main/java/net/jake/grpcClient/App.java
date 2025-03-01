package net.jake.grpcClient;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.InsecureChannelCredentials;
import common.grpc.GreeterGrpc;
import common.grpc.Helloworld;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        ManagedChannel channel = Grpc.newChannelBuilder("localhost:8999", InsecureChannelCredentials.create()).build();
        GreeterGrpc.GreeterBlockingStub bookStub = GreeterGrpc.newBlockingStub(channel);

        Helloworld.HelloReply reply = bookStub.sayHello(Helloworld.HelloRequest.newBuilder().setName("gRPC").build());
        System.out.println(reply.getMessage());

        channel.shutdown();
    }
}
