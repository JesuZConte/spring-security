package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByUserName(String userName) {
        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using username
        Query<User> query = currentSession.createQuery("from User where userName=:uName", User.class);
        query.setParameter("uName", userName);
        User user = null;

        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    @Override
    public void save(User user) {
        // get current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create the user
        currentSession.saveOrUpdate(user);
    }
}
