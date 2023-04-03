package com.example.progetto.RecyclerView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto.CardItem;
import com.example.progetto.R;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private List<CardItem> cardItemList;
    Activity activity;

    public CardAdapter(List<CardItem> list, Activity activity) {
        cardItemList = list;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);

        return new CardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItem currentCardItem = cardItemList.get(position);

        holder.placeTextView.setText(currentCardItem.getPlaceName());
        holder.dateTextView.setText(currentCardItem.getDate());
        String image = currentCardItem.getImageResource();
        if (image.contains("ic_")){
            Drawable drawable = AppCompatResources.getDrawable(activity, activity.getResources().getIdentifier(image, "drawable", activity.getPackageName()));
            holder.placeImageView.setImageDrawable(drawable);
        }
    }

    @Override
    public int getItemCount() {
        return cardItemList.size();
    }
}
