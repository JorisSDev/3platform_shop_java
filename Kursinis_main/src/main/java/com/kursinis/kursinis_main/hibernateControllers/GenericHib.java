package com.kursinis.kursinis_main.hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.ArrayList;
import java.util.List;

public class GenericHib {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager em;

    public GenericHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    //<T> indikuos, kad yra generic method. Ka tik padariau visu klasiu create backend metoda
    public <T> void create(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    //Ka tik padariau update visoms esybems
    public <T> void update(T entity) {
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }

    //Su delete bus niuansai, kol kas nerasau
    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = getEntityManager();
            tx = em.getTransaction();
            tx.begin();
            T object = em.find(entityClass, id);
            if (object != null) {
                em.remove(object);
                tx.commit();
            } else {
                System.out.println("Entity not found");
                // Optionally, you might want to throw an exception here
            }
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            e.printStackTrace();  // Consider logging this exception instead
        } finally {
            if (em != null) em.close();
        }
    }


    //Get by id visoms esybems READ
    public <T> T getEntityById(Class<T> entityClass, int id) {
        T result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //READ operacija, tik istrauks visus irasus, kurie yra DB
    public <T> List<T> getAllRecords(Class<T> entityClass) {
        EntityManager em = null;
        List<T> result = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaQuery query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = em.createQuery(query);
            result = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
        return result;
    }


}
