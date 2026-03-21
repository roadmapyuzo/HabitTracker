package com.example.habittracker.presentation.cards;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.habittracker.R;
import com.example.habittracker.domain.habit.Habit;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habit_card_screen);


        ViewPager2 viewPager = findViewById(R.id.mainViewPager);
        if (viewPager == null) {
            Log.e("CardsActivity", "ViewPager2 não encontrado!");
        }


        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit(1, "Drink Water", 3, true));
        habits.add(new Habit(2, "Exercise", 2, false));
        habits.add(new Habit(3, "Read Book", 1, true));


        CardsAdapter adapter = new CardsAdapter(habits);


        viewPager.setAdapter(adapter);


        viewPager.setPageTransformer((page, position) -> {
            float scale = 0.85f + (1 - Math.abs(position)) * 0.15f;
            page.setScaleY(scale);
        });


        viewPager.setClipToPadding(false);
        viewPager.setClipChildren(false);
        viewPager.setOffscreenPageLimit(3);
    }
}