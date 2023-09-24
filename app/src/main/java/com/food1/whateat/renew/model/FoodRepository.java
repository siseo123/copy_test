package com.food1.whateat.renew.model;

import java.util.ArrayList;
import java.util.List;

public class FoodRepository {

    private List<Food> foods = new ArrayList<>();

    public List<Food> getFoods() {
        return foods;
    }
}
