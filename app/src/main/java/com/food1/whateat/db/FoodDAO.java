package com.food1.whateat.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.food1.whateat.data.food.Food;

import java.util.List;

@Dao
public interface FoodDAO {

    @Insert
    void insert(Food food);
    @Delete
    void delete(Food food);
    @Query("SELECT * FROM foods")
    List<Food> getAll();
}