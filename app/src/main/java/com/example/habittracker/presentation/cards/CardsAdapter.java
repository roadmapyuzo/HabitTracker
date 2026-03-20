package com.example.habittracker.presentation.cards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;

import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.CardViewHolder> {

    private List<String> items;

    public CardsAdapter(List<String> items) {
        this.items = items;
    }

    // ViewHolder do card
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView image;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.cardTitle);
            description = itemView.findViewById(R.id.cardDescription);
            image = itemView.findViewById(R.id.cardImage);
        }
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.habit_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        holder.title.setText(items.get(position));
        holder.description.setText("Descrição do card " + (position + 1));
        holder.image.setImageResource(R.drawable.ic_launcher_background); // imagem de exemplo
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}