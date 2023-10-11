package com.food1.whateat.presentation;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.Food;
import com.food1.whateat.db.FoodDAO;
import com.food1.whateat.db.FoodDatabase;

import java.util.List;

public class FoodListActivity extends AppCompatActivity {

    private FoodDatabase foodDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        foodDatabase = FoodDatabase.getInstance(this);

        EditText foodText = findViewById(R.id.food_text);
        ImageButton addFoodBtn = findViewById(R.id.add_food_btn);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FoodDAO foodDAO = foodDatabase.foodDAO();
        List<Food> foods = foodDAO.getAll();
        FoodListAdapter foodListAdapter = new FoodListAdapter(this, foods, foodDAO);
        recyclerView.setAdapter(foodListAdapter);
        addFoodBtn.setOnClickListener(v -> {
            String foodName = foodText.getText().toString().trim();
            if (!foodName.equals(""))
            {
                Food food = new Food(foodName);
                foodDAO.insert(food);
                foodText.setText("");
                foods.clear();
                foods.addAll(foodDAO.getAll());
                foodListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
