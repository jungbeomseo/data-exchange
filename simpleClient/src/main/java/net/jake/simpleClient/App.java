package net.jake.simpleClient;

import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.stub.StreamObserver;
import io.grpc.InsecureChannelCredentials;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import common.grpc.UserMgmtGrpc;
import common.grpc.UserMgmtGrpc.UserMgmtBlockingStub;
import common.grpc.UserMgmtGrpc.UserMgmtStub;
import common.grpc.Usermgmt.RoleAddRequest;
import common.grpc.Usermgmt.User;
import common.grpc.Usermgmt.UserID;
import common.grpc.Usermgmt.UserRole;

public class App {
    public static void main(String[] args) throws Exception{
        GrpcClient grpcClient = new GrpcClient();
        // 1. add user
        User mockUser = User.newBuilder().setName("test %d".formatted(0)).build();
        grpcClient.addUserScenario(mockUser);
        // 2. get user
        grpcClient.getUserScenario();
        // 3. add admin role
        grpcClient.addRoleScenario(UserRole.Admin);
        // 4. addUsers
        grpcClient.addUsersScenario(getMockUsers());
        grpcClient.shutdown();
    }

    static private List<User> getMockUsers() {
        List<User> users = new ArrayList<User>();
        for (int i = 1; i < 5; i++) {
            User u = User.newBuilder().setName("test %d".formatted(i)).build();
            users.add(u);
        }
        return users;
    }

    static class GrpcClient {
        ManagedChannel channel = Grpc.newChannelBuilder("localhost:8999", InsecureChannelCredentials.create()).build();
        UserMgmtStub nonBlockingStub = UserMgmtGrpc.newStub(channel); // sync rpc
        UserMgmtBlockingStub blockingStub = UserMgmtGrpc.newBlockingStub(channel); // async rpc
        
        List<String> userIds = new ArrayList<>();

        public void addUserScenario(User user) {
            UserID reply = blockingStub.addUser(user);
            userIds.add(reply.getId());
            
            System.out.println("1. call RPC addUser with: User(\n%s)".formatted(user.toString()));
            System.out.println("> response: UserID(\n%s)\n".formatted(reply.toString()));
        }

        public void getUserScenario() {
            String userId = userIds.get(0);
            UserID req = UserID.newBuilder().setId(userId).build();
            User resp = blockingStub.getUser(req);

            System.out.println("2. call RPC getUser with: " + userId);
            System.out.println("> response: User(\n%s)\n".formatted(resp.toString()));
        }

        public void addRoleScenario(UserRole role) {
            RoleAddRequest roleRequest = RoleAddRequest.newBuilder().setId(userIds.get(0)).setRole(role).build();
            User resp = blockingStub.addRole(roleRequest);
            
            System.out.println("3. call RPC addRole with: RoleAddRequest(\n%s)".formatted(roleRequest.toString()));
            System.out.println("> response: User(\n%s)\n".formatted(resp.toString()));
        }

        public void addUsersScenario(List<User> mockUsers) throws InterruptedException {
            System.out.println("4. call RPC addUsers with: " + mockUsers.toString());

            List<String> addedUserIds = new ArrayList<>();
            StreamObserver<UserID> responseObserver = new StreamObserver<>() {
                @Override
                public void onNext(UserID userId) { addedUserIds.add(userId.getId()); }
                @Override
                public void onCompleted() { System.out.println("> Result: %s".formatted(addedUserIds.toString())); }
                @Override
                public void onError(Throwable t) {}
            };

            StreamObserver<User> requestObserver = nonBlockingStub.addUsers(responseObserver);
            try {
                for (var user : mockUsers) {
                    System.out.println(".. add user: %s".formatted(user.getName()));
                    requestObserver.onNext(user);
                }
            } catch (RuntimeException e) {
                requestObserver.onError(e);
                throw e;
            }
            requestObserver.onCompleted();
        }

        public void shutdown() throws InterruptedException {
            channel.awaitTermination(2, TimeUnit.SECONDS);
            channel.shutdown();
        }
    }
}