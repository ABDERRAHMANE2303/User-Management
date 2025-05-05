package org.usermanagement.service;

import org.usermanagement.dao.UserDao;
import org.usermanagement.entities.User;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;

@Stateless
public class UserService implements Serializable {

    @Inject
    private UserDao userDao;

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void saveOrUpdate(User user) {
        userDao.saveOrUpdate(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

    public User authenticate(String username, String password) {
        return userDao.authenticate(username, password);
    }

    // Additional methods for business logic, if needed
}