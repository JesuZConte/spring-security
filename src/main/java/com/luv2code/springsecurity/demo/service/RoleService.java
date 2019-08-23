package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAllRoles();
}
