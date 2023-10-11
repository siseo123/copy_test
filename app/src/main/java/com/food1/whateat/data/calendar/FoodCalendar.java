package com.food1.whateat.data.calendar;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "food_calendar", indices = {
        @Index(value = {"date"})
})
public class FoodCalendar {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_calendar_id")
    private long id;
    @ColumnInfo(name = "food_name")
    private final String foodName;
    private final LocalDateTime date;

    public FoodCalendar(String foodName, LocalDateTime date) {
        this.foodName = foodName;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
