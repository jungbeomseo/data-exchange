package net.jake.simpleServer.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.NonNull;
import net.jake.openapi.model.User;
import net.jake.openapi.model.UserRole;
import net.jake.simpleServer.UserMgmt.UserService;

@Controller
public class GraphQLUserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public User getUser(@Argument("id") String id) {
        return userService.getUser(id);
    }

    @MutationMapping
    public String addUser(@Argument("user") UserInput input) {
        return userService.addUser(input.toUser());
    }

    @MutationMapping
    public User addRole(
        @Argument("id") String id, 
        @Argument("role") @NonNull UserRole role
    ) {
        return userService.addRole(id, role);
    }

    public record UserInput(String name, List<UserRole> roles) {
        public User toUser() {
            return User.builder()
                .name(name)
                .roles(roles)
                .build();
        }
    }
}