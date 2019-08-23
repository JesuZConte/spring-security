package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {

    // inject the session factory
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Role findRoleByName(String roleName) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<Role> query = currentSession.createQuery("from Role where name=:rName", Role.class);
        query.setParameter("rName", roleName);
        Role role = null;
        try {
            role = query.getSingleResult();
        } catch (Exception e) {
            role = null;
        }
        return role;
    }
}
