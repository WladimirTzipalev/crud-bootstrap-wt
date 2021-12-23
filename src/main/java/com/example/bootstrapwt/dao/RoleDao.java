package com.example.bootstrapwt.dao;

import com.example.bootstrapwt.model.Role;
import java.util.List;

public interface RoleDao {
    List<Role> getAllRoles();

    Role findById(Long id);

    void saveRole(Role role);
}
