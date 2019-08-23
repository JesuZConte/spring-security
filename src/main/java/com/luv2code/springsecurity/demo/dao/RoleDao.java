package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.Role;

import java.util.List;

public interface RoleDao {

    Role findRoleByName(String theRoleName);

    List<Role> findAllRoles();

}
