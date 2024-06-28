package com.example.projeto;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


interface OnItemClickListenerReservation {
    void onItemClick(Reservation reservation);
}
public class ReservationAdapter extends RecyclerView.Adapter<ReservationViewHolder> {

    private Context context;
    private ArrayList<Reservation> items;

    private OnItemClickListenerReservation listener;

    public ReservationAdapter(Context context, ArrayList<Reservation> items,OnItemClickListenerReservation listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_reservation,parent,false);
        ReservationViewHolder viewHolder = new ReservationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        Reservation reservation = items.get(position);
        holder.name.setText(reservation.getRestaurant().getName());
      holder.date.setText(reservation.getDay());
      holder.hour.setText(reservation.getHour());
      Picasso.get().load(reservation.getRestaurant().getPhoto()).into(holder.photo);

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(reservation);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
