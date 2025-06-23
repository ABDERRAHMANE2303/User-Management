package org.usermanagement.listener;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.usermanagement.service.UserService;
import org.usermanagement.entities.User;

@WebListener
public class AppInitializationListener implements ServletContextListener {

    @Inject
    private UserService userService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Check if admin user exists, if not create one
        if (userService.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123"); // Consider using a secure password
            adminUser.setRole("admin");
            userService.saveOrUpdate(adminUser);
            
            System.out.println("Default admin user created successfully");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup code if needed
    }
}