package com.example.habittracker.presentation.calendar;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.app.DateProvider;
import com.example.habittracker.domain.dailyRecord.Record;
import com.example.habittracker.presentation.cards.CardsDisplayDataHolder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final Context context;
    private final CardsDisplayDataHolder item;
    private final DateProvider dateProvider;

    private final int numberOfMonths;
    private final Map<LocalDate, Integer> recordMap = new HashMap<>();
    private final int goal;

    public CalendarAdapter(Context context, CardsDisplayDataHolder item, DateProvider dateProvider) {
        this.context = context;
        this.item = item;
        this.dateProvider = dateProvider;
        this.goal = item.getHabit().getDailyGoal();
        for (Record record : item.getRecords()) {
            recordMap.put(record.getDate(), record.getNumberOfTimes());
        }

        this.numberOfMonths = calculateMonthSpan();
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.calendar_item, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, position - (getItemCount() - 1));
        holder.bind(calendar, recordMap, goal);
    }

    @Override
    public int getItemCount() {
        return numberOfMonths;
    }

    public void updateData(CardsDisplayDataHolder updatedItem) {

        this.recordMap.clear();
        for (Record record : updatedItem.getRecords()) {
            recordMap.put(record.getDate(), record.getNumberOfTimes());
        }

        notifyDataSetChanged();
    }
    private int calculateMonthSpan() {
        LocalDate oldest = getOldestLocalDate();
        if (oldest == null) return 1;

        LocalDate now = dateProvider.today();

        int months = (now.getYear() - oldest.getYear()) * 12 +
                (now.getMonthValue() - oldest.getMonthValue());

        return months + 1;
    }

    private LocalDate getOldestLocalDate() {
        LocalDate oldest = null;

        for (Record record : item.getRecords()) {
            LocalDate date = record.getDate();
            if (oldest == null || date.isBefore(oldest)) {
                oldest = date;
            }
        }

        return oldest;
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView monthYearText;
        GridLayout daysGrid;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            monthYearText = itemView.findViewById(R.id.monthYearText);
            daysGrid = itemView.findViewById(R.id.daysGrid);
        }

        void bind(Calendar calendar, Map<LocalDate, Integer> recordMap, int goal) {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            monthYearText.setText(getMonthName(month) + " " + year);

            daysGrid.removeAllViews();
            daysGrid.setColumnCount(7);

            int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int day = 1; day <= totalDays; day++) {
                TextView dayView = new TextView(itemView.getContext());
                dayView.setText(String.valueOf(day));
                dayView.setGravity(Gravity.CENTER);
                dayView.setTextColor(0xFFFFFFFF);

                LocalDate date = LocalDate.of(year, month + 1, day);
                Integer times = recordMap.get(date);

                if (times == null) {

                    dayView.setBackgroundResource(R.drawable.day_square_background);
                } else if (times < goal) {

                    dayView.setBackgroundResource(R.drawable.day_square_background);
                } else {

                    dayView.setBackgroundResource(R.drawable.day_green_calendar);
                }

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.setMargins(4, 4, 4, 4);

                dayView.setLayoutParams(params);
                daysGrid.addView(dayView);
            }
        }

        private String getMonthName(int month) {
            String[] monthNames = {
                    "January", "February", "March", "April",
                    "May", "June", "July", "August",
                    "September", "October", "November", "December"
            };
            return monthNames[month];
        }
    }
}