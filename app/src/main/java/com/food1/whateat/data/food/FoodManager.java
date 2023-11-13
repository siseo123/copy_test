package com.food1.whateat.data.food;

import java.util.ArrayList;
import java.util.List;

public class FoodManager {

    public static FoodManager INSTANCE;
    private final DefaultFoodRepository defaultFoodRepository = new DefaultFoodRepository();
    private List<FoodVO> selectFoodVOS = new ArrayList<>();
    private FoodVO selectedFoodVO = null;

    public DefaultFoodRepository getDefaultFoodRepository() {
        return defaultFoodRepository;
    }

    public static FoodManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FoodManager();
        }
        return INSTANCE;
    }
}
