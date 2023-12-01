package com.example.gofish.activities;

// For Java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

// CardAdapter.java
// ... (previous imports)

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Card> cards;

    public CardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    public void updateCardList(List<Card> newCards) {
        this.cards = newCards;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);

        // Set the card background
        holder.cardBackground.setImageResource(R.drawable.card_background);

        // Set the card rank and suit
        holder.cardRankTextView.setText(card.getRank());
        holder.cardSuitTextView.setText(card.getSuit());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cardBackground;
        TextView cardRankTextView;
        TextView cardSuitTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardBackground = itemView.findViewById(R.id.cardBackground);
            cardRankTextView = itemView.findViewById(R.id.cardRankTextView);
            cardSuitTextView = itemView.findViewById(R.id.cardSuitTextView);
        }
    }
}

