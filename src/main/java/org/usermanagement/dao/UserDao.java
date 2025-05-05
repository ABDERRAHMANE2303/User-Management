package org.usermanagement.dao;

import org.usermanagement.entities.User;

import java.util.List;

public interface UserDao {

    // Find user by ID
    User findById(Long id);

    // Find user by username
    User findByUsername(String username);

    // Get all users
    List<User> findAll();

    // Save or update user
    void saveOrUpdate(User user);

    // Delete user
    void delete(User user);

    // Delete user by ID
    void deleteById(Long id);

    // Check if username and password match (for authentication)
    User authenticate(String username, String password);
}