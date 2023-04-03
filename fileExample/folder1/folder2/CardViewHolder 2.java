package com.example.progetto.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto.R;

public class CardViewHolder extends RecyclerView.ViewHolder{

    ImageView placeImageView;
    TextView placeTextView;
    TextView dateTextView;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        placeImageView = itemView.findViewById(R.id.place_imageview);
        placeTextView = itemView.findViewById(R.id.place_textview);
        dateTextView = itemView.findViewById(R.id.date_textview);
    }
}
