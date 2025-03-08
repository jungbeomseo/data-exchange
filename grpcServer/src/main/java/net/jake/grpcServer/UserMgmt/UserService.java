package net.jake.grpcServer.UserMgmt;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.protobuf.Timestamp;

import common.grpc.Usermgmt.User;
import common.grpc.Usermgmt.UserRole;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;

@Service
public class UserService {
    Map<String, User> userMap = new HashMap<String, User>();

    public User getUser(@NonNull String id) {
        User user = Optional.of(id)
            .filter(StringUtils::isNotBlank)
            .map(Id -> userMap.get(Id))
            .orElse(null);
        return user;
    }

    public String addUser(@NonNull User input) {
        String userId = UUID.randomUUID().toString();
        User user = User.newBuilder(input)
            .setId(userId)
            // .setCreatedAt(getCurrentTime())
            .build();
        userMap.put(userId, user);
        return userId;
    }

    public User addRole(@NonNull String id, @NonNull UserRole role) {
        User user = getUser(id);
        if (user == null)
            return null;
        if (!user.getRolesList().contains(role)) {
            user = User.newBuilder(user).addRoles(role).build();
            userMap.put(user.getId(), user);
        }
        return user;
    }

    private Timestamp getCurrentTime() {
        Instant currentTime = Instant.now();
        return Timestamp.newBuilder()
            .setSeconds(currentTime.getEpochSecond())
            .setNanos(currentTime.getNano())
            .build();
    }
}
