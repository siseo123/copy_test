package com.food1.whateat.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.food1.whateat.data.calendar.FoodCalendar;
import com.food1.whateat.data.calendar.FoodCalendarDAO;
import com.food1.whateat.data.category.Category;
import com.food1.whateat.data.category.CategoryDAO;
import com.food1.whateat.data.category.CategoryFoodCrossRef;
import com.food1.whateat.data.food.FoodVO;
import com.food1.whateat.data.food.FoodDAO;

@Database(entities = {FoodVO.class, Category.class, CategoryFoodCrossRef.class,
        FoodCalendar.class
},
        version = 1,
        exportSchema = false)
@TypeConverters(value = {
        DateTypeConverter.class
})
public abstract class FoodDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "foods.db";
    private static FoodDatabase INSTANCE;

    public static FoodDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FoodDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    })
                    .build();
        }
        return INSTANCE;
    }
    public abstract CategoryDAO categoryDAO();
    public abstract FoodDAO foodDAO();
    public abstract FoodCalendarDAO foodCalendarDAO();
}
