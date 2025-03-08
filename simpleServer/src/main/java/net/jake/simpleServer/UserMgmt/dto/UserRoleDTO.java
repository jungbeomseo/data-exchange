package net.jake.simpleServer.UserMgmt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jake.simpleServer.UserMgmt.entity.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {
    UserRole role;
}
