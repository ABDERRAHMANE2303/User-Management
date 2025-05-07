package org.usermanagement.config;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JPAResources {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("userManagementPU");

    @Produces
    public EntityManager produceEntityManager() {
        return emf.createEntityManager();
    }
}
