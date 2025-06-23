package org.usermanagement.listener;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.usermanagement.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.usermanagement.entities.User;

@WebListener
public class AppInitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String url = System.getenv("JDBC_URL");
        String user = System.getenv("JDBC_USER");
        String password = System.getenv("JDBC_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new RuntimeException("Missing DB environment variables!");
        }

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", url);
        properties.put("jakarta.persistence.jdbc.user", user);
        properties.put("jakarta.persistence.jdbc.password", password);
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("userManagementPU", properties);
        EntityManager em = emf.createEntityManager();

        UserService userService = new UserService(em);

        if (userService.findByUsername("admin") == null) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setRole("admin");
            userService.saveOrUpdate(adminUser);
            System.out.println("Default admin user created successfully");
        }

        em.close();
        emf.close();
    }

    
}
