package com.food1.whateat.presentation.choice;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.food1.whateat.R;
import com.food1.whateat.data.category.Categories;
import com.food1.whateat.data.category.Category;
import com.food1.whateat.data.food.DefaultFoodRepository;
import com.food1.whateat.data.food.Food;
import com.food1.whateat.data.food.FoodDAO;
import com.food1.whateat.data.food.FoodManager;
import com.food1.whateat.data.food.FoodVO;
import com.food1.whateat.db.FoodDatabase;

import java.util.LinkedList;
import java.util.List;

public class ChoiceFoodActivity extends AppCompatActivity {

    private List<CheckBox> checkBoxList = new LinkedList<>();

    private List<Food> foods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        LinearLayout layout = findViewById(R.id.layout);

        FoodDatabase foodDatabase = FoodDatabase.getInstance(this);
        FoodDAO foodDAO = foodDatabase.foodDAO();
        List<FoodVO> foodsBySelected = foodDAO.findFoodsBySelected();

        Intent intent = getIntent();
        Category category = Categories.findByName(intent.getStringExtra("category"));
        DefaultFoodRepository defaultFoodRepository = FoodManager.getInstance().getDefaultFoodRepository();
        foods = defaultFoodRepository.getFoodsByCategory(category);
        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            CheckBox checkBox = new CheckBox(this);
            for (FoodVO foodVO : foodsBySelected) {
                if (food.getName().equals(foodVO.getName())) {
                    checkBox.setChecked(true);
                }
            }

            checkBox.setPadding(40, 20, 0, 10);
            checkBox.setId(i);
            checkBox.setText(food.getName());
            checkBox.setTextSize(50);
            checkBox.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#FF5722")));
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {

            });
            checkBoxList.add(checkBox);
            View underline = new View(this);
            LinearLayout.LayoutParams underlineParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    2
            );
            underlineParams.setMargins(0, 10, 0, 0);
            underline.setLayoutParams(underlineParams);
            underline.setBackgroundColor(Color.GRAY);
            linearLayout.addView(underline);

            layout.addView(checkBox);
            layout.addView(linearLayout);
        }

    }

    public void on_Click_sub(View v){
        Intent intent = new Intent();

        String[] selectedFoods = new String[checkBoxList.size()];
        for (int i = 0; i < checkBoxList.size(); i++) {
            CheckBox checkBox = checkBoxList.get(i);
            Food food = foods.get(i);
            if (checkBox.isChecked()) {
                selectedFoods[i] = "T|" + food.getName();
            } else {
                selectedFoods[i] = "F|" + food.getName();
            }
        }
        intent.putExtra("selected", selectedFoods);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
