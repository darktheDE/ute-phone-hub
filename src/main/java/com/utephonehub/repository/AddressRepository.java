package com.utephonehub.repository;

import com.utephonehub.config.DatabaseConfig;
import com.utephonehub.entity.Address;
import com.utephonehub.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class AddressRepository {
    
    public List<Address> findByUser(User user) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            TypedQuery<Address> q = em.createQuery("SELECT a FROM Address a WHERE a.user = :user", Address.class);
            q.setParameter("user", user);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Optional<Address> findById(Long id) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Address.class, id));
        } finally {
            em.close();
        }
    }
    
    public Address save(Address address) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
            return address;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public Address update(Address address) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Address merged = em.merge(address);
            em.getTransaction().commit();
            return merged;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public void delete(Address address) {
        EntityManager em = DatabaseConfig.getEntityManager();
        try {
            em.getTransaction().begin();
            Address managed = em.merge(address);
            em.remove(managed);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
