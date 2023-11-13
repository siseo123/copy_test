package com.food1.whateat.presentation.selected_list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.FoodVO;

import java.util.List;


public class SelectFoodItemAdapter extends RecyclerView.Adapter<SelectFoodItemAdapter.ViewHolder> {

    private final Context context;
    private final List<FoodVO> foodVOList;

    public SelectFoodItemAdapter(Context context, List<FoodVO> foodVOList) {
        this.context = context;
        this.foodVOList = foodVOList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodVO foodVO = foodVOList.get(position);
        holder.textView.setText(foodVO.getName());
        holder.checkBox.setOnClickListener(v -> {

        });
        if (foodVO.isLike()) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return foodVOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_favorite);
            textView = view.findViewById(R.id.textView);
        }
    }
}
