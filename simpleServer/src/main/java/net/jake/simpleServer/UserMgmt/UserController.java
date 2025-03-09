package net.jake.simpleServer.UserMgmt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import net.jake.openapi.api.UsersApi;
import net.jake.openapi.model.User;
import net.jake.openapi.model.UserRoleDTO;

@Controller
public class UserController implements UsersApi {
    @Autowired 
    UserService userService;

    @Override
    public ResponseEntity<User> getUser(String id) {
        User res = Optional.ofNullable(userService.getUser(id))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found: %s".formatted(id)));
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> addUser(@Valid User user) {
        String res = userService.addUser(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<User> addRole(String id, @Valid UserRoleDTO role) {
        User res = Optional.ofNullable(userService.addRole(id, role.getRole()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found: %s".formatted(id)));
        return new ResponseEntity<>(res, HttpStatus.OK);
        
    }
}