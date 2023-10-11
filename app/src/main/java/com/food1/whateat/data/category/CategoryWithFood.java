package com.food1.whateat.data.category;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.food1.whateat.data.food.Food;

import java.util.List;

public class CategoryWithFood {
    @Embedded
    private final Category category;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "food_id",
            associateBy = @Junction(CategoryFoodCrossRef.class)
    )
    private final List<Food> foods;

    public CategoryWithFood(Category category, List<Food> foods) {
        this.category = category;
        this.foods = foods;
    }

    public Category getCategory() {
        return category;
    }

    public List<Food> getFoods() {
        return foods;
    }
}