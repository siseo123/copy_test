package com.food1.whateat.data.category;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.food1.whateat.data.food.FoodVO;

import java.util.List;

public class CategoryWithFood {
    @Embedded
    private final Category category;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "food_id",
            associateBy = @Junction(CategoryFoodCrossRef.class)
    )
    private final List<FoodVO> foodVOS;

    public CategoryWithFood(Category category, List<FoodVO> foodVOS) {
        this.category = category;
        this.foodVOS = foodVOS;
    }

    public Category getCategory() {
        return category;
    }

    public List<FoodVO> getFoods() {
        return foodVOS;
    }
}