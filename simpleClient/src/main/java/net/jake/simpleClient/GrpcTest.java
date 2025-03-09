package net.jake.simpleClient;

import java.util.ArrayList;
import java.util.List;

import common.grpc.UserMgmtGrpc;
import common.grpc.UserMgmtGrpc.UserMgmtBlockingStub;
import common.grpc.Usermgmt.RoleAddRequest;
import common.grpc.Usermgmt.User;
import common.grpc.Usermgmt.UserID;
import common.grpc.Usermgmt.UserRole;
import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;

public class GrpcTest {
    List<String> userIds = new ArrayList<>();

    public void test() {
        ManagedChannel channel = Grpc.newChannelBuilder("localhost:8999", InsecureChannelCredentials.create()).build();
        UserMgmtBlockingStub blockingStub = UserMgmtGrpc.newBlockingStub(channel); // async rpc

        System.out.println("\n>> [2] GRPC TEST SCENARIO");

        // 1. add user
        User mockUser = User.newBuilder().setName("test %d".formatted(0)).build();
        UserID uid = blockingStub.addUser(mockUser); // rpc call
        userIds.add(uid.getId());            
        System.out.println("\n1. created user:\n%s".formatted(uid.getId()));

        // 2. get user
        User user = blockingStub.getUser(uid); // rpc call
        System.out.println("\n2. query user:\n%s".formatted(user.toString()));
        
        // 3. add role
        RoleAddRequest roleRequest = RoleAddRequest.newBuilder()
            .setId(uid.getId())
            .setRole(UserRole.Admin)
            .build();
        user = blockingStub.addRole(roleRequest);
        System.out.println("\n3. add role:\n%s".formatted(user.toString()));

        channel.shutdown();
    }
}
