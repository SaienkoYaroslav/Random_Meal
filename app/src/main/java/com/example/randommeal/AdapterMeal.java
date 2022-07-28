package com.example.randommeal;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMeal extends RecyclerView.Adapter<AdapterMeal.ViewHolderMeal>{

    private List<Meal> meals;

    public AdapterMeal(List<Meal> meals) {
        this.meals = meals;
    }

    private OnMealClickListener onMealClickListener;

    interface OnMealClickListener{
        void onMealClick(int position);
    }

    public void setOnMealClickListener(OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }

    @NonNull
    @Override
    public ViewHolderMeal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolderMeal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMeal holder, int position) {
        Meal meal = meals.get(position);
        holder.tvMealName.setText(meal.getMealName());
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    class ViewHolderMeal extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView tvMealName;
        private CardView cardView;

        public ViewHolderMeal(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.textView_rv_meal_name);
            cardView = itemView.findViewById(R.id.card_view_item);
            cardView.setOnCreateContextMenuListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMealClickListener != null) {
                        onMealClickListener.onMealClick(getAdapterPosition());
                    }
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.select_an_option);
            menu.add(this.getAdapterPosition(), 121, 0, R.string.edit);
            menu.add(this.getAdapterPosition(), 122, 1, R.string.delete);
        }
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
