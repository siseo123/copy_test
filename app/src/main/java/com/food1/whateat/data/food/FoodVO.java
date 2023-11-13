package com.food1.whateat.data.food;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "foods",
        indices = {@Index(value = "name", unique = true)})
public class FoodVO {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id")
    private int id;
    @ColumnInfo(name = "name")
    private final String name;
    private boolean like = false;
    private boolean selected = false;

    public FoodVO(String name) {
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

}
