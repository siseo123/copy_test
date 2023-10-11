package com.food1.whateat.data.food;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//TODO: name unique
@Entity(tableName = "foods")
public class Food {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    private int id;
    @ColumnInfo(name = "name")
    private final String name;
    private boolean like = false;
    private boolean selected = false;

    public Food(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

//    public static Builder builder(String name) {
//        return new Builder(name);
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//
//
//        private String name;
//
//        public Builder(String name) {
//            this.name = name;
//        }
//
//        public Builder() {
//        }
//
//        public Builder category(Category category) {
//            return this;
//        }
//
//        public Food build() {
//            Food food = new Food();
//            return food;
//        }
//    }
//
//    public static enum DefaultFoods {
//        //**//
//    }
}
