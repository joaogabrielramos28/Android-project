package com.example.projeto;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantViewHolder  extends RecyclerView.ViewHolder {
    TextView name,description,category,rating;
    ImageView photo;
    public RestaurantViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.restaurantName);
        description = itemView.findViewById(R.id.restaurantDesc);
        category = itemView.findViewById(R.id.restaurantCategory);
        rating = itemView.findViewById(R.id.restaurantRating);
        photo = itemView.findViewById(R.id.restaurantImg);


    }
}
