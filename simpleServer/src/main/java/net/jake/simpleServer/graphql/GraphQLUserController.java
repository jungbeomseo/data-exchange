package net.jake.simpleServer.graphql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import net.jake.simpleServer.UserMgmt.User;
import net.jake.simpleServer.UserMgmt.UserService;
import net.jake.simpleServer.UserMgmt.User.UserRole;

@Controller
public class GraphQLUserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public User getUser(@Argument("id") String id) {
        try {
            return userService.getUser(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @MutationMapping
    public String addUser(@Argument("user") UserInput input) {
        return userService.addUser(input.toUser());
    }

    @MutationMapping
    public User addRole(
        @Argument("id") String id, 
        @Argument("role") UserRole role
    ) {
        try {
            return userService.addRole(id, role);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public record UserInput(String name, List<UserRole> roles) {
        public User toUser() {
            return new User(null, name, roles);
        }
    }
}