package com.food1.whateat.renew.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;

import com.food1.whateat.R;
import com.food1.whateat.renew.db.FoodDatabase;
import com.food1.whateat.renew.model.Food;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;

    private FoodDatabase foodDatabase;

    private List<String> viewFoods = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_calendar);

        viewFoods.add("사과");
        viewFoods.add("배");
        ListView listView = findViewById(R.id.listview);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewFoods);
        listView.setAdapter(listViewAdapter);

        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                viewFoods.clear();
                viewFoods.addAll(foodDatabase.findFoodByDate(year, month, dayOfMonth));
                listViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        foodDatabase = new FoodDatabase(this);
        foodDatabase.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        foodDatabase.close();

    }
}