package com.example.projeto;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ReservationViewHolder extends RecyclerView.ViewHolder {
    TextView name,hour,date;
    ImageView photo;
    Button deleteBtn;
    public ReservationViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.reservationNameText);
        hour = itemView.findViewById(R.id.hourText);
        date = itemView.findViewById(R.id.DateText);
        photo = itemView.findViewById(R.id.reservationImgPhoto);
        deleteBtn = itemView.findViewById(R.id.cancelReservationBtn);

    }
}
