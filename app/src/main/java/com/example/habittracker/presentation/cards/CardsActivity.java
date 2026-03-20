package com.example.habittracker.presentation.cards;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habittracker.R;

import java.util.Arrays;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_card_screen); // Layout da sua tela com ViewPager2

        // Pegando referência do ViewPager2
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        if(viewPager == null){
            Log.e("CardsActivity", "ViewPager2 não encontrado!");
        }



        // Lista de exemplo de cards
        List<String> items = Arrays.asList("Card 1", "Card 2", "Card 3", "Card 4");

        // Criando o Adapter
        CardsAdapter adapter = new CardsAdapter(items);

        // Setando o adapter no ViewPager2
        viewPager.setAdapter(adapter);

        // Efeito de escala opcional (destaque para o card central)
        viewPager.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
        });

        // Espaço lateral opcional para ver parcialmente o próximo card
        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
    }
}