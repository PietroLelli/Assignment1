package com.example.progettomobile.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettomobile.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    ImageView productImageView;
    TextView productTextView;
    TextView productPriceTextView;
    TextView productDescription;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        productImageView = itemView.findViewById(R.id.product_imageview);
        productTextView = itemView.findViewById(R.id.product_textview);
        productPriceTextView = itemView.findViewById(R.id.productPrice_textview);
        productDescription = itemView.findViewById(R.id.productDescription_textview);
    }
}
