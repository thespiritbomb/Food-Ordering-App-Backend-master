package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.ItemDao;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public List<ItemEntity> getItemsByCategoryAndRestaurant(String someRestaurantId, String uuid) {
        List<ItemEntity> itemEntities = new ArrayList<>();
        return itemEntities;
    }

    public ItemEntity getItemById(String itemId) {
       ItemEntity itemEntity = itemDao.getItemById(itemId);
       return itemEntity;
    }

    public List<ItemEntity> getItemsByPopularity(RestaurantEntity restaurantEntity) {
        List<ItemEntity> itemEntities = new ArrayList<>();
        return  itemEntities;
    }
}
