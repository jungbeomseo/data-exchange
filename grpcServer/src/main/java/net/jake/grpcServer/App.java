package net.jake.grpcServer;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import common.grpc.GreeterGrpc;
import common.grpc.Helloworld;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            Server server = ServerBuilder.forPort(8999).addService(new GreeterImpl()).build();
            server.start();
            System.out.println("Server started at " + server.getPort());
            server.awaitTermination();
        } catch (IOException e) {
            System.out.println("Error: " + e);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e);
        }
    }

    static class GreeterImpl extends GreeterGrpc.GreeterImplBase {
        @Override
        public void sayHello(
            Helloworld.HelloRequest request, 
            StreamObserver<Helloworld.HelloReply> responseObserver
        ) {
            var reply = Helloworld.HelloReply
                .newBuilder()
                .setMessage("Hello " + request.getName())
                .build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
