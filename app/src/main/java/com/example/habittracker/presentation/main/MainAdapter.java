package com.example.habittracker.presentation.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.app.DateProvider;
import com.example.habittracker.domain.habit.Habit;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainDisplayDataHolder> dataList;
    private MainViewModel viewModel;

    private Set<Integer> expandedItems = new HashSet<>();

    public MainAdapter(List<MainDisplayDataHolder> dataList,MainViewModel viewModel) {
        this.dataList = dataList;
        this.viewModel = viewModel;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.habit_item_list, parent, false);

        return new MainViewHolder(view);

    }

    public void updateData(List<MainDisplayDataHolder> newDataList) {
        this.dataList = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        MainDisplayDataHolder data = dataList.get(position);

        holder.txtTitle.setText(data.getHabit().getName());

        holder.progressDotsContainer.removeAllViews();

        int dailyGoal = data.getHabit().getDailyGoal();
        int goalStatus = data.getGoalStatus();

        for (int i = 0; i < dailyGoal; i++) {

            if (expandedItems.contains(position)) {
                holder.expandArea.setVisibility(View.VISIBLE);
            } else {
                holder.expandArea.setVisibility(View.GONE);
            }

            if (goalStatus > 0) {

                ImageView dot = new ImageView(holder.itemView.getContext());
                dot.setImageResource(R.drawable.active_circle);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0); // espaço entre dots
                dot.setLayoutParams(params);

                holder.progressDotsContainer.addView(dot);

                goalStatus = goalStatus - 1;

            } else {
                ImageView dot = new ImageView(holder.itemView.getContext());
                dot.setImageResource(R.drawable.circle);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(4, 0, 4, 0);
                dot.setLayoutParams(params);

                holder.progressDotsContainer.addView(dot);
            }

            holder.buttonCheck.setOnClickListener(v -> {
                viewModel.incrementHabit(data.getHabit());
            });

        }

        for (int i = 0; i < 7; i++) {

            LocalDate day = LocalDate.now().minusDays(6).plusDays(i);

            String dayAbbrev = day.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

            if (data.getWeekStatus().get(i)) {
                holder.daySquares[i].setBackgroundResource(R.drawable.day_square_complete_background);
            } else {
                holder.daySquares[i].setBackgroundResource(R.drawable.day_square_background);
            }

            holder.dayLabels[i].setText(dayAbbrev);
        }

        holder.area1.setOnClickListener(v -> {
            if (holder.expandArea.getVisibility() == View.GONE) {
                expandView(holder.expandArea);
                expandedItems.add(position);
            } else {
                collapseView(holder.expandArea);
                expandedItems.remove(position);
            }
        });

        holder.area2.setOnClickListener(v -> {
            if (holder.expandArea.getVisibility() == View.GONE) {
                expandView(holder.expandArea);
                expandedItems.add(position);
            } else {
                collapseView(holder.expandArea);
                expandedItems.remove(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        LinearLayout progressDotsContainer;
        LinearLayout buttonCheck;
        TextView[] dayLabels = new TextView[7];
        View[] daySquares = new View[7];
        LinearLayout area1;
        LinearLayout area2;
        ConstraintLayout expandArea;
        public MainViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtHabitName);
            progressDotsContainer = itemView.findViewById(R.id.progressDotsContainer);
            buttonCheck = itemView.findViewById(R.id.area3);
            area1 = itemView.findViewById(R.id.area1);
            area2 = itemView.findViewById(R.id.area2);
            expandArea = itemView.findViewById(R.id.expandArea);

            dayLabels[0] = itemView.findViewById(R.id.day1Label);
            dayLabels[1] = itemView.findViewById(R.id.day2Label);
            dayLabels[2] = itemView.findViewById(R.id.day3Label);
            dayLabels[3] = itemView.findViewById(R.id.day4Label);
            dayLabels[4] = itemView.findViewById(R.id.day5Label);
            dayLabels[5] = itemView.findViewById(R.id.day6Label);
            dayLabels[6] = itemView.findViewById(R.id.day7Label);

            daySquares[0] = itemView.findViewById(R.id.day1Square);
            daySquares[1] = itemView.findViewById(R.id.day2Square);
            daySquares[2] = itemView.findViewById(R.id.day3Square);
            daySquares[3] = itemView.findViewById(R.id.day4Square);
            daySquares[4] = itemView.findViewById(R.id.day5Square);
            daySquares[5] = itemView.findViewById(R.id.day6Square);
            daySquares[6] = itemView.findViewById(R.id.day7Square);
        }

    }

    private void expandView(final View view) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(((View)view.getParent()).getWidth(), View.MeasureSpec.EXACTLY),
                View.MeasureSpec.UNSPECIFIED
        );
        final int targetHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        animator.setDuration(200);
        animator.start();
    }

    private void collapseView(final View view) {
        final int initialHeight = view.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            view.getLayoutParams().height = (int) animation.getAnimatedValue();
            view.requestLayout();
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.setDuration(300);
        animator.start();
    }


}
