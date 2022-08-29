package org.darius.configuration;


import org.darius.entity.User;
import org.darius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MockGenerateUserData {

    private UserService userService;
    private final static int NUMBER_OF_USERS_TO_GENERATE = 20;
    private final MockUserSupplier supplier = MockUserSupplier.supplier;
    private List<User> customUserList;

    public MockGenerateUserData() {
        customUserList = new ArrayList<>();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
        //initializeUsers();
    }

    private void initializeUsers() {
        /*
        Set<Role> roles = supplier.rolesSupplier.get();
        if (userService.roleCount() < 2) {
            roles = userService.addRoles(roles);
            if (userService.userCount() < 20) {
                for (int numberOfEntriesInserted = 0; numberOfEntriesInserted < NUMBER_OF_USERS_TO_GENERATE; numberOfEntriesInserted++) {
                    Role role = null;
                    if (numberOfEntriesInserted < 4 && numberOfEntriesInserted > 0) {
                        role = roles.stream().filter(roleToFilter -> roleToFilter.getRoleName().equals("ROLE_Admin")).findFirst().get();
                    } else {
                        role = roles.stream().filter(roleToFilter -> roleToFilter.getRoleName().equals("ROLE_User")).findFirst().get();
                    }
                    this.customUserList.add(addUser(supplier.userSupplier.apply(role)));
                }
                this.customUserList.stream().forEach(user -> userService.insertUser(user));
            }
        }
         */
    }

    public User addUser(User customUser) {
        this.customUserList.add(customUser);
        return customUser;
    }

}
