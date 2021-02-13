package exam.bdcc.securityservice.service;

import exam.bdcc.securityservice.entities.AppRole;
import exam.bdcc.securityservice.entities.AppUser;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(AppUser appUser);
    AppRole addNewRole(AppRole appRole);
    void addRoleToUser(String username, String roleName);
    AppUser loadUserByUsername(String username);
    List<AppUser> listUsers();
}
