package com.example.habittracker.presentation.cards;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habittracker.MyApplication;
import com.example.habittracker.R;
import com.example.habittracker.di.AppContainer;
import com.example.habittracker.domain.habit.Habit;
import com.example.habittracker.presentation.main.MainActivity;
import com.example.habittracker.presentation.main.MainDisplayDataHolder;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private CardsAdapter cardsAdapter;
    private CardsViewModel cardsViewModel;
    private AppContainer appContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_card_screen);

        appContainer = new AppContainer(getApplicationContext());

        cardsViewModel = appContainer.getCardsViewModel();
        cardsAdapter = appContainer.getCardsAdapter();

        cardsViewModel.displayData.observe(this, new Observer<List<CardsDisplayDataHolder>>() {
            @Override
            public void onChanged(List<CardsDisplayDataHolder> displayDataHolders) {
                cardsAdapter.updateData(displayDataHolders);
            }
        });

        viewPager = findViewById(R.id.mainViewPager);
        if (viewPager == null) {
            Log.e("CardsActivity", "ViewPager2 não encontrado!");
            return;
        }

        viewPager.setAdapter(cardsAdapter);


        viewPager.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
        });

        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);

        LinearLayout btnHome = findViewById(R.id.buttonHome);

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(CardsActivity.this, MainActivity.class);
            startActivity(intent);
        });


        cardsViewModel.loadData();


    }
}