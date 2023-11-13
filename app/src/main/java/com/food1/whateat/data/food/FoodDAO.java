package com.food1.whateat.data.food;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDAO {

    /**
     *
     * @param foodVO
     * @return 중복된 값이 존재하는 경우 -1 반환
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(FoodVO foodVO);
    @Update
    void update(FoodVO foodVO);
    @Delete
    void delete(FoodVO foodVO);
    @Query("SELECT * FROM foods WHERE name = :foodName")
    FoodVO findByFoodName(String foodName);

    @Query("SELECT * FROM foods")
    List<FoodVO> getAll();

    @Transaction
    @Query("SELECT * FROM foods WHERE name = :foodName")
    FoodWithCategory getCategoryWithFood(String foodName);

    @Transaction
    @Query("SELECT * FROM foods")
    List<FoodWithCategory> getAllCategoryWithFood();

    @Query("SELECT * FROM foods WHERE selected = 1")
    List<FoodVO> findFoodsBySelected();

    @Query("SELECT * FROM foods WHERE `like` = 1")
    List<FoodVO> findFoodsByLiked();
}