package org.usermanagement.dao;

import org.usermanagement.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    public UserDaoImpl() {
        emf = Persistence.createEntityManagerFactory("userManagementPU");
    }
    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    private EntityManager getEntityManager() {
        // Use the manually injected EM if present
        if (this.em != null) {
            return this.em;
        }

        // Default fallback
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
        }
        return em;
    }

    private void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public User findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public List<User> findAll() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u ORDER BY u.surname, u.name", User.class);
            return query.getResultList();
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void saveOrUpdate(User user) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (user.getId() == null) {
                em.persist(user);
            } else {
                em.merge(user);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void delete(User user) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (!em.contains(user)) {
                user = em.merge(user);
            }
            em.remove(user);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            closeEntityManager();
        }
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            delete(user);
        }
    }

    @Override
    public User authenticate(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            closeEntityManager();
        }
    }
}