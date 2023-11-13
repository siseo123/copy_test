package com.food1.whateat.presentation.add_food;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.DefaultFoodRepository;
import com.food1.whateat.data.food.FoodDAO;
import com.food1.whateat.data.food.FoodManager;
import com.food1.whateat.data.food.FoodVO;
import com.food1.whateat.db.FoodDatabase;
import com.food1.whateat.presentation.add_food.adapter.FoodListAdapter;
import com.food1.whateat.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

public class AddFoodListActivity extends AppCompatActivity {

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

        List<FoodVO> foodVOList = new ArrayList<>();
        DefaultFoodRepository defaultFoodRepository = FoodManager.getInstance().getDefaultFoodRepository();
        foodDAO.getAll().forEach(foodVO -> {
            if (!defaultFoodRepository.containFoodByName(foodVO.getName())) {
                foodVOList.add(foodVO);
            }
        });

        FoodListAdapter foodListAdapter = new FoodListAdapter(this, foodVOList, foodDAO);
        recyclerView.setAdapter(foodListAdapter);
        addFoodBtn.setOnClickListener(v -> {
            String foodName = foodText.getText().toString().trim();
            if (foodName.equals("")) {
                ViewUtils.showToast(getApplicationContext(), "추가하실 음식을 입력해주세요.");
                return;
            }

            if (defaultFoodRepository.containFoodByName(foodName)) {
                ViewUtils.showToast(getApplicationContext(), "기본으로 등록 되어있는 음식 입니다.");
                return;
            }

            FoodVO foodVO = new FoodVO(foodName);
            foodVO.setSelected(true);
            long result = foodDAO.insert(foodVO);
            if (result == -1) {
                ViewUtils.showToast(getApplicationContext(), "중복 등록은 안됩니다.");
                return;
            }
            foodText.setText("");
            foodVOList.clear();
            foodVOList.addAll(foodDAO.getAll());
            foodListAdapter.notifyDataSetChanged();

        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
