package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class ItemDao {
    @PersistenceContext
    private EntityManager entityManager;

    public ItemEntity getItemById(String itemId) {
        try {
            return entityManager.createNamedQuery("getItemById", ItemEntity.class).setParameter("uuid", itemId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }


}
