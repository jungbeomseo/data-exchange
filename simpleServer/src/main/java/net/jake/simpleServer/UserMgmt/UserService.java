package net.jake.simpleServer.UserMgmt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import net.jake.openapi.model.User;
import net.jake.openapi.model.UserRole;


@Service
public class UserService {
    Map<String, User> userMap = new HashMap<String, User>();

    public User getUser(@NonNull String id) {
        return Optional.of(id)
            .filter(StringUtils::isNotBlank)
            .map(Id -> userMap.get(Id))
            .orElse(null);
    }

    public String addUser(@NonNull User user) {
        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        userMap.put(userId, user);
        return userId;
    }

    public User addRole(@NonNull String id, @NonNull UserRole role) {
        User user = getUser(id);
        List<UserRole> roles = Optional.ofNullable(user.getRoles())
            .orElse(new ArrayList<>());
        if (!roles.contains(role)) {
            roles.add(role);
            user.setRoles(roles);
            userMap.put(id, user);
        }
        return user;
    }
}
