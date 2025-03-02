package net.jake.simpleServer.UserMgmt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.NonNull;
import net.jake.simpleServer.UserMgmt.User.UserRole;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired 
    UserService userService;

    @GetMapping()
    public User getUser(@RequestParam("id") String id) {
        try {
            return userService.getUser(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public String addUser(@RequestBody @NonNull User user) {
        return userService.addUser(user);
    }

    @PostMapping("/{id}/roles")
    public User addRole(@PathVariable("id") String id, @RequestParam("role") @NonNull UserRole role) {
        try {
            return userService.addRole(id, role);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
