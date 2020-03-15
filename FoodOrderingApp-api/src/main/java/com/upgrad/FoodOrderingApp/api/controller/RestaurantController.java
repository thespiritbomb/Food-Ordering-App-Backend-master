package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.InvalidRatingException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantDetailsResponse>> getAllRestaurants() throws RestaurantNotFoundException {

        List<RestaurantEntity> restaurantEntityList = new ArrayList<>();
        restaurantEntityList = restaurantService.getAllRestaurants();                               //Calling getAllRestaurant function from restaurant Service
        List<RestaurantCategoryEntity> categoryEntities = new ArrayList<>();
        List<RestaurantDetailsResponse> restaurantDetailsResponse = new ArrayList<>();

        for (RestaurantEntity restaurantEntity : restaurantEntityList) {                            //Iterating RestaurantEntity to set Response
            categoryEntities = restaurantService.getCategoryByRestaurant(restaurantEntity);         //Calling getCategoryByRestaurant

            List<CategoryList> categoryLists = new ArrayList<>();
            String str = null;
            List<String> strList = new ArrayList<>();

            for(RestaurantCategoryEntity ce:categoryEntities)                                       //Iterating RestaurantCategoryEntity
            {
                strList.add(ce.getCategoryId().getCategoryName());                                  //Adding Category Name to the list
                Collections.sort(strList);                                                          //Sorting the list
            }
            if(strList.size() >0) {                                                                 //Checking if list is not empty
                str = strList.get(0);                                                               //Adding first
                for (int i = 1; i < strList.size()-1; i++) {
                    str = str.concat(","+strList.get(i));
                }
                if (strList.size()-1 > 1)
                    str = str.concat(","+strList.get(strList.size()-1));
            }

            CategoryList categoryList = new CategoryList();
            categoryList.setCategoryName(str);

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
            restaurantDetailsResponse.add(new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName()).photoURL(restaurantEntity.getPhotoUrl())
            .customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAvgPrice()).numberCustomersRated(restaurantEntity.getNumberCustomersRated())
            .address(addressList).categories(categoryLists));
        }

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/name/{reastaurant_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByName(@PathVariable("reastaurant_name") final String reastaurant_name) throws RestaurantNotFoundException {
        List<RestaurantEntity> restaurantEntityList = restaurantService.getRestaurantByRestaurantName(reastaurant_name);


        List<RestaurantDetailsResponse> restaurantResponseList = new ArrayList<>();
        for (RestaurantEntity restaurantEntity:restaurantEntityList)
        {
            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getAddress().getState().getStateName());

            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setId(restaurantEntity.getAddress().getUuid());
            restaurantDetailsResponseAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber());
            restaurantDetailsResponseAddress.setLocality(restaurantEntity.getAddress().getLocality());
            restaurantDetailsResponseAddress.setCity(restaurantEntity.getAddress().getCity());
            restaurantDetailsResponseAddress.setPincode(restaurantEntity.getAddress().getCity());
            restaurantDetailsResponseAddress.setState(restaurantDetailsResponseAddressState);

            List<RestaurantCategoryEntity> restaurantCategoryEntityList = restaurantService.getCategoryByRestaurant(restaurantEntity);
            List<CategoryList> categoryLists = new ArrayList<>();
            String str = null;
            List<String> strList = new ArrayList<>();

            for(RestaurantCategoryEntity ce:restaurantCategoryEntityList)
            {
                strList.add(ce.getCategoryId().getCategoryName());
                Collections.sort(strList);
            }
            if(strList.size() > 0) {
                str = strList.get(0);
                for (int i = 1; i < strList.size()-1; i++) {
                    str = str.concat(","+strList.get(i));
                }
                if (strList.size()-1 > 1)
                    str = str.concat(","+strList.get(strList.size()-1));
            }

            CategoryList categoryList = new CategoryList();
            categoryList.setCategoryName(str);
            categoryLists.add(categoryList);

            restaurantResponseList.add(new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName())
                    .photoURL(restaurantEntity.getPhotoUrl()).customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAvgPrice()).numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                    .address(restaurantDetailsResponseAddress).categories(categoryLists));
        }

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantResponseList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/restaurant/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<RestaurantDetailsResponse>> getRestaurantByCategoryId(@PathVariable("category_id") final String category_id) throws CategoryNotFoundException, RestaurantNotFoundException {
        List<RestaurantCategoryEntity> restaurantEntityList = restaurantService.restaurantByCategory(category_id);

        List<RestaurantDetailsResponse> restaurantResponseList = new ArrayList<>();

        for (RestaurantCategoryEntity restaurantEntity : restaurantEntityList) {
           // restaurantResponseList.add(new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getRestaurantId().getUuid())));
            RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
            restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getRestaurantId().getAddress().getState().getUuid()));
            restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getRestaurantId().getAddress().getState().getStateName());

            RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
            restaurantDetailsResponseAddress.setId(restaurantEntity.getRestaurantId().getAddress().getUuid());
            restaurantDetailsResponseAddress.setFlatBuildingName(restaurantEntity.getRestaurantId().getAddress().getFlatBuilNumber());
            restaurantDetailsResponseAddress.setLocality(restaurantEntity.getRestaurantId().getAddress().getLocality());
            restaurantDetailsResponseAddress.setCity(restaurantEntity.getRestaurantId().getAddress().getCity());
            restaurantDetailsResponseAddress.setPincode(restaurantEntity.getRestaurantId().getAddress().getCity());
            restaurantDetailsResponseAddress.setState(restaurantDetailsResponseAddressState);

            List<RestaurantCategoryEntity> restaurantCategoryEntityList = restaurantService.getCategoryByRestaurant(restaurantEntity.getRestaurantId());
            List<CategoryList> categoryLists = new ArrayList<>();
            String str = null;
            List<String> strList = new ArrayList<>();

            for(RestaurantCategoryEntity ce:restaurantCategoryEntityList)
            {
                strList.add(ce.getCategoryId().getCategoryName());
                Collections.sort(strList);
            }
            if(strList.size() > 0) {
                str = strList.get(0);
                for (int i = 1; i < strList.size()-1; i++) {
                    str = str.concat(","+strList.get(i));
                }
                if (strList.size()-1 > 1)
                    str = str.concat(","+strList.get(strList.size()-1));
            }

            CategoryList categoryList = new CategoryList();
            categoryList.setCategoryName(str);
            categoryLists.add(categoryList);

            restaurantResponseList.add(new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getRestaurantId().getUuid())).restaurantName(restaurantEntity.getRestaurantId().getRestaurantName())
                    .photoURL(restaurantEntity.getRestaurantId().getPhotoUrl()).customerRating(BigDecimal.valueOf(restaurantEntity.getRestaurantId().getCustomerRating())).averagePrice(restaurantEntity.getRestaurantId().getAvgPrice())
                    .numberCustomersRated(restaurantEntity.getRestaurantId().getNumberCustomersRated())
                    .address(restaurantDetailsResponseAddress).categories(categoryLists));
        }

        return new ResponseEntity<List<RestaurantDetailsResponse>>(restaurantResponseList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/api/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantDetailsResponse> getRestaurantByRestaurantId(@PathVariable("restaurant_id") final String restaurant_id) throws RestaurantNotFoundException {
        RestaurantEntity restaurantEntity = restaurantService.getRestaurantByRestaurantId(restaurant_id);

        RestaurantDetailsResponse restaurantResponseList = new RestaurantDetailsResponse();
        RestaurantDetailsResponseAddressState restaurantDetailsResponseAddressState = new RestaurantDetailsResponseAddressState();
        restaurantDetailsResponseAddressState.setId(UUID.fromString(restaurantEntity.getAddress().getState().getUuid()));
        restaurantDetailsResponseAddressState.setStateName(restaurantEntity.getAddress().getState().getStateName());

        RestaurantDetailsResponseAddress restaurantDetailsResponseAddress = new RestaurantDetailsResponseAddress();
        restaurantDetailsResponseAddress.setId(restaurantEntity.getAddress().getUuid());
        restaurantDetailsResponseAddress.setFlatBuildingName(restaurantEntity.getAddress().getFlatBuilNumber());
        restaurantDetailsResponseAddress.setLocality(restaurantEntity.getAddress().getLocality());
        restaurantDetailsResponseAddress.setCity(restaurantEntity.getAddress().getCity());
        restaurantDetailsResponseAddress.setPincode(restaurantEntity.getAddress().getCity());
        restaurantDetailsResponseAddress.setState(restaurantDetailsResponseAddressState);

        List<RestaurantCategoryEntity> restaurantCategoryEntityList = restaurantService.getCategoryByRestaurant(restaurantEntity);
        List<CategoryList> categoryLists = new ArrayList<>();
        List<CategoryItemEntity> categoryItemEntityList = new ArrayList<>();
        List<ItemList> itemLists = new ArrayList<>();
        for(RestaurantCategoryEntity restaurantCategoryEntity: restaurantCategoryEntityList)
            {
                CategoryList categoryList = new CategoryList();
                categoryList.setId(UUID.fromString(restaurantCategoryEntity.getCategoryId().getUuid()));
                categoryList.setCategoryName(restaurantCategoryEntity.getCategoryId().getCategoryName());
                categoryItemEntityList = restaurantService.getItemByCategoryId(restaurantCategoryEntity);
                for(CategoryItemEntity categoryItemEntity: categoryItemEntityList)
                {
                    ItemList itemList = new ItemList();
                    itemList.setId(UUID.fromString(categoryItemEntity.getItemId().getUuid()));
                    itemList.setItemName(categoryItemEntity.getItemId().getItemName());
                    itemList.setPrice(categoryItemEntity.getItemId().getPrice());
                    itemList.setItemType(ItemList.ItemTypeEnum.fromValue(categoryItemEntity.getItemId().getType()));
                    itemLists.add(itemList);
                }
                categoryLists.add(categoryList);
            }


            restaurantResponseList = new RestaurantDetailsResponse().id(UUID.fromString(restaurantEntity.getUuid())).restaurantName(restaurantEntity.getRestaurantName())
                    .photoURL(restaurantEntity.getPhotoUrl()).customerRating(BigDecimal.valueOf(restaurantEntity.getCustomerRating())).averagePrice(restaurantEntity.getAvgPrice()).numberCustomersRated(restaurantEntity.getNumberCustomersRated())
                    .address(restaurantDetailsResponseAddress).categories(categoryLists);

        return new ResponseEntity<RestaurantDetailsResponse>(restaurantResponseList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/api/restaurant/{restaurant_id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RestaurantUpdatedResponse> updateRestaurantDetails(@PathVariable("restaurant_id") final String restaurant_id, @RequestHeader("authorization") final String authorization,
                                                                             @RequestHeader("customerRating") final Double customerRating) throws AuthorizationFailedException, RestaurantNotFoundException, InvalidRatingException {
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setUuid(restaurant_id);
        restaurantEntity.setCustomerRating(customerRating);
        CustomerEntity customerEntity = customerService.getCustomer(authorization);

        RestaurantEntity restaurantDetailsEntity = restaurantService.updateRestaurantRating(restaurantEntity, customerRating);

        RestaurantUpdatedResponse restaurantUpdatedResponse = new RestaurantUpdatedResponse().id(UUID.fromString(restaurantDetailsEntity.getUuid())).status("RESTAURANT RATING UPDATED SUCCESSFULLY");
        return new ResponseEntity<RestaurantUpdatedResponse>(restaurantUpdatedResponse, HttpStatus.OK);
    }

}
