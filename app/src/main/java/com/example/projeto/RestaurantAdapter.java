package com.example.projeto;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

interface OnItemClickListener {
    void onItemClick(Restaurant restaurant);
}

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private Context context;
    private ArrayList<Restaurant> items;

    private OnItemClickListener listener;


    public RestaurantAdapter(Context context, ArrayList<Restaurant> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_restaurant,parent,false);
        RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = items.get(position);


        holder.name.setText(restaurant.getName());
        holder.category.setText(restaurant.getCategory());
        holder.description.setText(restaurant.getDescription());
        holder.rating.setText(restaurant.getRate().toString());
        Picasso.get().load(restaurant.getPhoto()).into(holder.photo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(restaurant);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
