package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.User;

public interface UserDao {

    void save(User user);
    User findByUserName(String userName);
}
