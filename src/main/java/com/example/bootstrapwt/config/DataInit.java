package com.example.bootstrapwt.config;

import com.example.bootstrapwt.model.Role;
import com.example.bootstrapwt.model.User;
import com.example.bootstrapwt.service.RoleService;
import com.example.bootstrapwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DataInit {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public DataInit(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void init() {
        Role role0 = new Role("ADMIN");
        Role role1 = new Role("USER");
        Role role2 = new Role("MODERATOR");
        roleService.saveRole(role0);
        roleService.saveRole(role1);
        roleService.saveRole(role2);

        User user0 = new User();
        user0.setEmail("admin@gmail.com");
        user0.setPassword("admin");
        user0.setName("Admin");
        user0.setSurname("Adminov");
        user0.setAge((byte) 33);
        user0.addRole(role0);
        user0.addRole(role1);

        User user1 = new User();
        user1.setEmail("user@gmail.com");
        user1.setPassword("user");
        user1.setName("User");
        user1.setSurname("Userov");
        user1.setAge((byte) 33);
        user1.addRole(role1);

        User user2 = new User();
        user2.setEmail("moderator@gmail.com");
        user2.setPassword("moderator");
        user2.setName("Moderator");
        user2.setSurname("Moderatorov");
        user2.setAge((byte) 33);
        user2.addRole(role2);

        userService.save(user0);
        userService.save(user1);
        userService.save(user2);
    }
}
