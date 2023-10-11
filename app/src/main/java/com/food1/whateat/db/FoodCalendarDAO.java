package com.food1.whateat.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.food1.whateat.data.calendar.FoodCalendar;

import java.util.List;

@Dao
public interface FoodCalendarDAO {

    @Insert
    void insert(FoodCalendar foodCalendar);

    @Query("SELECT * FROM food_calendar " +
            "WHERE strftime('%Y', date) = :year " +
            "AND strftime('%m', date) = :month " +
            "AND strftime('%d', date) = :dayOfMonth")
    List<FoodCalendar> getFoodCalendarByDate(int year, int month, int dayOfMonth);

}
