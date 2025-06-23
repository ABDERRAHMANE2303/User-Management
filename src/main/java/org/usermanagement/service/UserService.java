package org.usermanagement.service;

import org.usermanagement.dao.UserDao;
import org.usermanagement.dao.UserDaoImpl;
import org.usermanagement.entities.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;

import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class UserService implements Serializable {

    private UserDao userDao;

    public UserService() {
        // Create the DAO directly instead of relying on @Inject
        this.userDao = new UserDaoImpl();
    }
    public UserService(EntityManager em) {
        this.userDao = new UserDaoImpl(em);
    }

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
}