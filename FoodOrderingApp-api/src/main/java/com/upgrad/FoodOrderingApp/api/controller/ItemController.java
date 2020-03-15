/*
package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<CategoriesListResponse>> getAllRestaurants(){

        List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
        restaurantEntityList = restaurantService.getAllRestaurants();
        List<CategoryEntity> categoryEntities = new ArrayList<>();
        List<RestaurantDetailsResponse> restaurantDetailsResponse = new ArrayList<>();

        for (RestaurantEntity restaurantEntity : restaurantEntityList) {
            categoryEntities = categoryService.getCategoriesByRestaurant(restaurantEntity.getId().toString());
            CategoryList categoryList = new CategoryList();
            for(CategoryEntity ce:categoryEntities)
            {
                categoryList.setCategoryName(ce.getCategoryName());
            }
            List<CategoryList> categoryLists = new ArrayList<>();
            categoryLists.add(categoryList);


            RestaurantDetailsResponseAddressState addressListState = new RestaurantDetailsResponseAddressState();
            addressListState.setId(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()));
            addressListState.setStateName(restaurantEntity.getAddress().getState().getStateName());

            RestaurantDetailsResponseAddress addressList = new RestaurantDetailsResponseAddress();
            addressList.setId(restaurantEntity.getAddress().getUuid());
            addressList.setFlatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber());
            addressList.setLocality(restaurantEntity.getAddress().getLocality());
            addressList.setCity(restaurantEntity.getAddress().getCity());
            addressList.setPincode(restaurantEntity.getAddress().getPincode());
            addressList.setState(addressListState);
            restaurantDetailsResponse.add(new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).photoURL(restaurantEntity.getPhotoUrl())
                    .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAvgPrice()).numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                    .address(addressList).categories(categoryLists));
        }

        return new ResponseEntity<List<CategoriesListResponse>>(categoriesListResponse, HttpStatus.OK);
    }


}
*/
