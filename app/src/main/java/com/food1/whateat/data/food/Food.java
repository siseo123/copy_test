package com.food1.whateat.data.food;

import com.food1.whateat.data.category.Category;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private final String name;
    private List<Category> categories = new ArrayList<>();

    public Food(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static class Builder {


        private String name;
        private List<Category> categories = new ArrayList<>();
        public Builder(String name) {
            this.name = name;
        }

        public Builder() {
        }

        public Builder addCategory(Category category) {
            categories.add(category);
            return this;
        }

        public Food build() {
            Food food = new Food(name);
            food.setCategories(categories);
            return food;
        }
    }
}
