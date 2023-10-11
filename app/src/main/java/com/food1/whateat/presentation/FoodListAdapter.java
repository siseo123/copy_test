package com.food1.whateat.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.Food;
import com.food1.whateat.db.FoodDAO;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private final Context context;
    private final List<Food> foods;
    private final FoodDAO foodDAO;

    public FoodListAdapter(Context context, List<Food> foods, FoodDAO foodDAO) {
        this.context = context;
        this.foods = foods;
        this.foodDAO = foodDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foods.get(position);
        holder.textView.setText(food.getName());

        holder.btDelete.setOnClickListener(v -> {
            foodDAO.delete(foods.get(holder.getAdapterPosition()));
            foods.remove(holder.getAdapterPosition());
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, foods.size());
        });
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            textView = view.findViewById(R.id.text_view);
            btEdit = view.findViewById(R.id.bt_edit);
            btDelete = view.findViewById(R.id.bt_delete);
        }
    }

}
