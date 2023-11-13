package com.food1.whateat.presentation.add_food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.food1.whateat.R;
import com.food1.whateat.data.food.FoodDAO;
import com.food1.whateat.data.food.FoodVO;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    private final Context context;
    private final List<FoodVO> foodVOS;
    private final FoodDAO foodDAO;

    public FoodListAdapter(Context context, List<FoodVO> foodVOS, FoodDAO foodDAO) {
        this.context = context;
        this.foodVOS = foodVOS;
        this.foodDAO = foodDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_food_list_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodVO foodVO = foodVOS.get(position);
        holder.textView.setText(foodVO.getName());

        holder.btDelete.setOnClickListener(v -> {
            foodDAO.delete(foodVOS.get(holder.getAdapterPosition()));
            foodVOS.remove(holder.getAdapterPosition());
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, foodVOS.size());
        });
    }

    @Override
    public int getItemCount() {
        return foodVOS.size();
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
