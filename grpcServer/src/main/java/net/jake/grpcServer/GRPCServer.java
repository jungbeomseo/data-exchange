package net.jake.grpcServer;

import org.springframework.beans.factory.annotation.Autowired;

import common.grpc.UserMgmtGrpc.UserMgmtImplBase;
import common.grpc.Usermgmt.RoleAddRequest;
import common.grpc.Usermgmt.User;
import common.grpc.Usermgmt.UserID;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.jake.grpcServer.UserMgmt.UserService;

@GrpcService
public class GRPCServer extends UserMgmtImplBase {
    @Autowired
    private UserService userService;

    @Override
    public void getUser(
        UserID userId, 
        StreamObserver<User> responseObserver
    ) {
        User resp = userService.getUser(userId.getId());
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void addUser(User user, StreamObserver<UserID> responseObserver) {
        String userId = userService.addUser(user);
        UserID resp = UserID.newBuilder().setId(userId).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void addRole(RoleAddRequest req, StreamObserver<User> responseObserver) {
        User user = userService.addRole(req.getId(), req.getRole());
        responseObserver.onNext(user);        
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<User> addUsers(
        StreamObserver<UserID> responseObserver
    ) {
        StreamObserver<User> observer = new StreamObserver<User>() {
            @Override
            public void onNext(User user) {
                String userId = userService.addUser(user);
                UserID resp = UserID.newBuilder().setId(userId).build();
                responseObserver.onNext(resp);
            }
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
            @Override
            public void onError(Throwable error) {}
        };
        return observer;
    }
}
