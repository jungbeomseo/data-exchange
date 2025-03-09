package net.jake.simpleClient;

public class App {
    public static void main(String[] args) throws Exception{
        OpenApiTest openApi = new OpenApiTest();
        openApi.test();

        GrpcTest grpc = new GrpcTest();
        grpc.test();
    }

}