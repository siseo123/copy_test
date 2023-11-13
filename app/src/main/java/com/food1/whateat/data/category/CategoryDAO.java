package com.food1.whateat.data.category;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.food1.whateat.data.category.Category;
import com.food1.whateat.data.category.CategoryFoodCrossRef;
import com.food1.whateat.data.category.CategoryWithFood;

@Dao
public interface CategoryDAO {

    @Insert
    void insert(Category category);

    @Insert
    void addCategoryWithFood(CategoryFoodCrossRef categoryFoodCrossRef);

    @Transaction
    @Query("SELECT * FROM food_categories WHERE name = :categoryName")
    CategoryWithFood getCategoryWithFood(String categoryName);
}