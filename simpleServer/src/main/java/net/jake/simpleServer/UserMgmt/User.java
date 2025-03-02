package net.jake.simpleServer.UserMgmt;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String id;
    String name;
    List<UserRole> roles;

    public enum UserRole {
        Admin,
        Manager,
        Employee
    }
}
