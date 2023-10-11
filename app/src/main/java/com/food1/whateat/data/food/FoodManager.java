package com.food1.whateat.data.food;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {

    public static final FoodManager INSTANCE = new FoodManager();
    private final FoodRepository defaultFoodRepository = new FoodRepository();
    private final FoodRepository foodRepository = new FoodRepository();
    private List<Food> selectFoods = new ArrayList<>();

    public FoodManager() {
//        FoodRepository.addDefault(defaultFoodRepository);
    }

    public FoodRepository getDefaultFoodRepository() {
        return defaultFoodRepository;
    }

    public FoodRepository getFoodRepository() {
        return foodRepository;
    }
}
