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

import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    private final Context context;
    private final int numberOfMonths;

    public CalendarAdapter(Context context, int numberOfMonths) {
        this.context = context;
        this.numberOfMonths = numberOfMonths;
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
        calendar.add(Calendar.MONTH, position); // incrementa o mês conforme a posição
        holder.bind(calendar);
    }

    @Override
    public int getItemCount() {
        return numberOfMonths;
    }

    static class CalendarViewHolder extends RecyclerView.ViewHolder {

        TextView monthYearText;
        GridLayout daysGrid;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            monthYearText = itemView.findViewById(R.id.monthYearText);
            daysGrid = itemView.findViewById(R.id.daysGrid);
        }

        void bind(Calendar calendar) {
            // Mostrar Mês + Ano
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH); // 0-11
            monthYearText.setText(getMonthName(month) + " " + year);

            // Limpar grid antes de preencher
            daysGrid.removeAllViews();
            daysGrid.setColumnCount(7);

            int totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int day = 1; day <= totalDays; day++) {
                TextView dayView = new TextView(itemView.getContext());
                dayView.setText(String.valueOf(day));
                dayView.setGravity(Gravity.CENTER);
                dayView.setTextColor(0xFFFFFFFF); // branco
                dayView.setBackgroundColor(0xFF000000); // preto = não completado

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