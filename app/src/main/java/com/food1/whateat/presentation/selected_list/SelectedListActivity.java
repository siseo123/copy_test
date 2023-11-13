package com.food1.whateat.presentation.selected_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.FoodDAO;
import com.food1.whateat.data.food.FoodVO;
import com.food1.whateat.db.FoodDatabase;
import com.food1.whateat.presentation.selected_list.adapter.SelectFoodItemAdapter;

import java.util.List;


public class SelectedListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleted_list);


        FoodDatabase foodDatabase = FoodDatabase.getInstance(this);
        FoodDAO foodDAO = foodDatabase.foodDAO();
        List<FoodVO> foodVO = foodDAO.findFoodsBySelected();

        findViewById(R.id.inform_btn);

        RecyclerView recyclerView = findViewById(R.id.rv_selected_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SelectFoodItemAdapter(this, foodVO));
    }

    public void on_Click_sub(View v){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }


}