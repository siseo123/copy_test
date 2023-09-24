package com.food1.whateat.renew.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.food1.whateat.renew.model.Food;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodDatabase {

    private static final String DATABASE_NAME = "food.db";
    private static final String TABLE = "FOOD";
    private static final int DATABASE_VERSION = 3;

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public FoodDatabase(Context context) {
        this.context = context;
    }


    public boolean open(){

        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getReadableDatabase();

        return true;
    }

    public void close()
    {
        db.close();
    }

    public List<String> findFoodByDate(int year, int month, int dayOfMonth) {
        ArrayList<String> foods = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE day = '" + toDate(year, month, dayOfMonth) + "'", null);
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                String food = cursor.getString(cursor.getColumnIndexOrThrow("food"));
                foods.add(food);
            }
        }
        return foods;
    }

    public void addFood(Food food, int year, int month, int dayOfMonth) {
        db.execSQL("INSERT INTO FOOD (food, day) VALUES('" + food.getId() + "', '" + toDate(year, month, dayOfMonth) + "')");
    }

    private static String toDate(int year, int month, int dayOfMonth) {
        StringBuilder date = new StringBuilder();
        date.append(year);
        date.append("-");
        date.append(month);
        date.append("-");
        date.append(dayOfMonth);
        return date.toString();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name,factory,version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + "("
                    + "id   INTEGER         NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "food VARCHAR(100)    NOT NULL DEFAULT '', "
                    + "day  DATE            NOT NULL " +
                    ")"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
