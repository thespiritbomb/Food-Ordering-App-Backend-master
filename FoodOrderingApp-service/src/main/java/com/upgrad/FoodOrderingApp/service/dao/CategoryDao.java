package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantCategoryEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CategoryEntity getCategoryById(String categoryId) {
        try {
            return entityManager.createNamedQuery("getCategoryById", CategoryEntity.class).setParameter("uuid", categoryId).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }



    public TypedQuery<CategoryEntity> getALLCategory() {
        try {
            return entityManager.createNamedQuery("getALLCategories", CategoryEntity.class);
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CategoryItemEntity> getItemByCategoryId(CategoryEntity categoryEntity) {
        try {
            return entityManager.createNamedQuery("getItemByCategoryId", CategoryItemEntity.class).setParameter("categoryId", categoryEntity).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }

    /*public List<RestaurantCategoryEntity> getCategoriesByRestaurant(Integer restaurantId) {
        try {
            return entityManager.createNamedQuery("getCategoriesByRestaurant", RestaurantCategoryEntity.class).setParameter("restaurantId", restaurantId).getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }*/


}
