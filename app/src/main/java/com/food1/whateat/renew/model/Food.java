package com.food1.whateat.renew.model;

import java.util.List;

public class Food {

    private final String id;
    private Category category;
    private List<String> ingredients;
    private List<String> tastes;

    public Food(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
