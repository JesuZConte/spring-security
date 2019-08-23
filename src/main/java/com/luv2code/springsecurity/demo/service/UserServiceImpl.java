package com.luv2code.springsecurity.demo.service;

import com.luv2code.springsecurity.demo.dao.RoleDao;
import com.luv2code.springsecurity.demo.dao.UserDao;
import com.luv2code.springsecurity.demo.entity.Role;
import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.user.CrmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void save(CrmUser crmUser) {
        User user = new User();

        // assign user details to the user object
        user.setUserName(crmUser.getUserName());
        user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
        user.setFirstName(crmUser.getFirstName());
        user.setLastName(crmUser.getLastName());
        user.setEmail(crmUser.getEmail());

        // give user default role of "employee"

        if(crmUser.getFormRole() != "ROLE_EMPLOYEE") {
            user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE"), roleDao.findRoleByName(crmUser.getFormRole())));
        } else {
            user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));
        }

        // save user in the database
        userDao.save(user);

    }

    @Override
    @Transactional
    public User findByUserName(String userName) {
        // check if username already exists in database
        User user = userDao.findByUserName(userName);
        return user;
    }

//    @Override
//    @Transactional
//    public void update(User user) {
//        userDao.update(user);
//
//    }
//
//    @Override
//    @Transactional
//    public void deleteByUserName(String userName) {
//        userDao.deleteByUserName(userName);
//    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
