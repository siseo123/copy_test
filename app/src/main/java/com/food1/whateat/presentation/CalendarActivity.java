package com.food1.whateat.presentation;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.food1.whateat.R;
import com.food1.whateat.data.calendar.FoodCalendar;
import com.food1.whateat.db.FoodCalendarDAO;
import com.food1.whateat.db.FoodDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private FoodDatabase database;
    private FoodCalendarDAO foodCalendarDAO;
    private List<String> viewFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_calendar);

        ListView listView = findViewById(R.id.listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewFoods);
        listView.setAdapter(listViewAdapter);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                viewFoods.clear();
                Log.d("aa", year + " " + month + " " + dayOfMonth);
                List<FoodCalendar> foodCalendarByDate = foodCalendarDAO.getFoodCalendarByDate(2023, 10, 10);
                System.out.println(foodCalendarByDate);
                foodCalendarDAO.getFoodCalendarByDate(year, (month+1), dayOfMonth).forEach(foodCalendar -> {
                    Log.d("calendar", foodCalendar.getFoodName());
                });
                List<String> collect = foodCalendarDAO.getFoodCalendarByDate(year, (month+1), dayOfMonth)
                        .stream()
                        .map(foodCalendar -> foodCalendar.getFoodName())
                        .collect(Collectors.toList());
                viewFoods.addAll(collect);
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        database = FoodDatabase.getInstance(this);
        foodCalendarDAO = database.foodCalendarDAO();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        foodCalendarDatabase.close();

    }
}