package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CategoriesListResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.CategoryListResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CategoryItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoriesListResponse> getAllCategories(){

        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        CategoriesListResponse categoriesListResponse = new CategoriesListResponse();
        categoryEntityList = categoryService.getAllCategories();

        List<CategoryListResponse> categoryListResponse = new ArrayList<>();

        for (CategoryEntity ce : categoryEntityList) {
            categoryListResponse.add(new CategoryListResponse().id(UUID.fromString(ce.getUuid())).categoryName(ce.getCategoryName())
                    );
        }
        if(!categoryListResponse.isEmpty())
         categoriesListResponse = new CategoriesListResponse().categories(categoryListResponse);

        return new ResponseEntity<CategoriesListResponse>(categoriesListResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryDetailsResponse> getCategoryById( @PathVariable("category_id") final String categoryId) throws CategoryNotFoundException {

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity = categoryService.getCategoryById(categoryId);

        List<ItemEntity> itemEntityList = new ArrayList<>();
        itemEntityList = categoryService.getItemsById(categoryEntity);
        CategoryDetailsResponse categoryListResponse = new CategoryDetailsResponse();
        categoryListResponse = new CategoryDetailsResponse().id(UUID.fromString(categoryEntity.getUuid())
        ).categoryName(categoryEntity.getCategoryName());
        for (ItemEntity itemEntity:itemEntityList)
        {
            ItemList itemList = new ItemList();
            itemList.setId(UUID.fromString(itemEntity.getUuid()));
            itemList.setItemName(itemEntity.getItemName());
            itemList.setPrice(itemEntity.getPrice());
            itemList.setItemType(ItemList.ItemTypeEnum.fromValue(itemEntity.getType()));
            categoryListResponse.addItemListItem(itemList);
        }



        return new ResponseEntity<CategoryDetailsResponse>(categoryListResponse, HttpStatus.OK);
    }


}
