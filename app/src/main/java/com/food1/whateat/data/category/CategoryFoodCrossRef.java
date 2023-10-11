package com.food1.whateat.data.category;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"category_id", "food_id"})
public class CategoryFoodCrossRef {
    @ColumnInfo(name = "category_id")
    private final int categoryId;
    @ColumnInfo(name = "food_id")
    private final int foodId;

    public CategoryFoodCrossRef(int categoryId, int foodId) {
        this.categoryId = categoryId;
        this.foodId = foodId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getFoodId() {
        return foodId;
    }
}
