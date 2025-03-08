package net.jake.simpleServer.UserMgmt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import net.jake.simpleServer.UserMgmt.dto.UserRoleDTO;
import net.jake.simpleServer.UserMgmt.entity.User;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired 
    UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") @NotBlank String id) {
        return Optional.ofNullable(userService.getUser(id))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found: %s".formatted(id)));
    }

    @PostMapping()
    public String addUser(@RequestBody @Valid User user) {
        return userService.addUser(user);
    }

    @PostMapping("/{id}/roles")
    public User addRole(@PathVariable("id") String id, @RequestBody @Valid UserRoleDTO role) {
        return Optional.ofNullable(userService.addRole(id, role.getRole()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found: %s".formatted(id)));
    }
}