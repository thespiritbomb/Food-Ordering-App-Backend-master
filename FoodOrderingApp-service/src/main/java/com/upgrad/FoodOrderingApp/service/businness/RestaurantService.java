package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.CustomerDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantDao restaurantDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CustomerDao customerDao;


    /**
     * The method implements the business logic for getAllQuestions endpoint.
     */
    public List<RestaurantEntity> getAllRestaurants(){
        List<RestaurantEntity>  restaurantEntity = new ArrayList<>();
        restaurantEntity = restaurantDao.getAllRestaurants().getResultList();
        return restaurantEntity;
    }


    public List<RestaurantEntity> restaurantsByRating() {
        List<RestaurantEntity> restaurantEntities =new ArrayList<>();
        return restaurantEntities;
    }

    /*public List<RestaurantEntity> getRestaurantsByName(String someRestaurantName) throws RestaurantNotFoundException {
        if(someRestaurantName == null)
        {
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntities =new ArrayList<>();
        restaurantEntities = restaurantDao.getRestaurantByRestaurantName(someRestaurantName);
        return restaurantEntities;
    }*/

    public List<RestaurantCategoryEntity> restaurantByCategory(String someCategoryId) throws CategoryNotFoundException {
        if(someCategoryId == null)
        {
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }

        CategoryEntity categoryEntity = categoryDao.getCategoryById(someCategoryId);
        if(categoryEntity == null)
        {
            throw new CategoryNotFoundException("CNF-002","No category by this id");
        }


        List<RestaurantCategoryEntity> restaurantEntities =new ArrayList<>();
        restaurantEntities = restaurantDao.restaurantsByCategoryId(categoryEntity).getResultList();
        return restaurantEntities;
    }




    public RestaurantEntity getRestaurantByRestaurantId(String restaurantId) throws RestaurantNotFoundException {
        if(restaurantId == null)
        {
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }
        RestaurantEntity restaurantEntity = restaurantDao.restaurantsByRestaurantId(restaurantId);
        if(restaurantEntity == null)
        {
            throw new RestaurantNotFoundException("RNF-001","No restaurant by this id");
        }

        return restaurantEntity;
    }

    public List<RestaurantEntity> getRestaurantByRestaurantName(String restaurantName) throws RestaurantNotFoundException {
        if(restaurantName == null)
        {
            throw new RestaurantNotFoundException("RNF-003","Restaurant name field should not be empty");
        }
        List<RestaurantEntity> restaurantEntity = restaurantDao.getRestaurantByRestaurantName(restaurantName);

        return restaurantEntity;
    }

    public List<RestaurantCategoryEntity> getCategoryByRestaurant(RestaurantEntity restaurantEntity) throws RestaurantNotFoundException {

        List<RestaurantCategoryEntity> restaurantCategoryEntity = new ArrayList<>();
        restaurantCategoryEntity = restaurantDao.getCategoryByRestaurant(restaurantEntity);
        return restaurantCategoryEntity;
    }

    public List<CategoryItemEntity> getItemByCategoryId(RestaurantCategoryEntity  restaurantCategoryEntity) throws RestaurantNotFoundException {

        List<CategoryItemEntity> categoryItemEntity = categoryDao.getItemByCategoryId(restaurantCategoryEntity.getCategoryId());

        return categoryItemEntity;
    }




    @Transactional(propagation = Propagation.REQUIRED)
    public RestaurantEntity updateRestaurantRating(RestaurantEntity restaurantEntity, Double cu) throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException {


        if(restaurantEntity.getId() == null)
        {
            throw new RestaurantNotFoundException("RNF-002","Restaurant id field should not be empty");
        }

        if(restaurantEntity.getCustomerRating() == null || (restaurantEntity.getCustomerRating() < 1 && restaurantEntity.getCustomerRating() > 5))
        {
            throw new InvalidRatingException("IRE-001","Restaurant should be in the range of 1 to 5");
        }

        RestaurantEntity rEntity = restaurantDao.restaurantsByRestaurantId(restaurantEntity.getUuid());
        if(rEntity.getId() == restaurantEntity.getId())
        {
            restaurantEntity.setCustomerRating(restaurantEntity.getCustomerRating());
            restaurantEntity.setNumberCustomersRated(rEntity.getNumberCustomersRated() + 1);
            return restaurantDao.updateRestaurantEntity(restaurantEntity);
        }
        return restaurantEntity;
    }

}
