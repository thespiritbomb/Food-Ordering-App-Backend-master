package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.dao.RestaurantDao;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private RestaurantDao restaurantDao;

    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        categoryEntities = categoryDao.getALLCategory().getResultList();
        return categoryEntities;
    }

    public CategoryEntity getCategoryById(String category_id) throws CategoryNotFoundException {
        CategoryEntity categoryEntities = new CategoryEntity();
        categoryEntities =categoryDao.getCategoryById(category_id);
        if(category_id == null)
        {
            throw new CategoryNotFoundException("CNF-001","Category id field should not be empty");
        }
        if(categoryEntities == null)
        {
            throw new CategoryNotFoundException("CNF-002","No category by this id");
        }
        return categoryEntities;
    }

    /*public List<CategoryEntity> getCategoriesByRestaurant(String restaurantUUid) {
        List<RestaurantCategoryEntity> restaurantCategoryEntity = new ArrayList<>();
        RestaurantEntity restaurantEntity = restaurantDao.restaurantsByRestaurantId(restaurantUUid);
        restaurantCategoryEntity = categoryDao.getCategoriesByRestaurant(restaurantEntity.getId());
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        for(RestaurantCategoryEntity rce : restaurantCategoryEntity)
        {
            categoryEntities.add(categoryDao.getCategoryById(rce.getCategoryId().getUuid()));
        }

        return categoryEntities;
    }*/

    public List<CategoryEntity> getAllCategoriesOrderedByName() {
        List<CategoryEntity> categoryEntities = new ArrayList<>();

        return categoryEntities;
    }

    public List<ItemEntity> getItemsById(CategoryEntity categoryEntity) {
        ItemEntity itemEntity = new ItemEntity();
        List<ItemEntity> itemEntities = new ArrayList<>();
        List<CategoryItemEntity>  categoryItemEntity = new ArrayList<>();
        categoryItemEntity = categoryDao.getItemByCategoryId(categoryEntity);
        for (CategoryItemEntity ce: categoryItemEntity)
        {
           itemEntity = itemDao.getItemById(ce.getItemId().getUuid());
           itemEntities.add(itemEntity);
        }
        return  itemEntities;
    }

}
