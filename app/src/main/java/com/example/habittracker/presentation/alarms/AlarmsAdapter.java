package com.example.habittracker.presentation.alarms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmViewHolder> {

    private List<AlarmsDisplayDataHolder> datalist;

    public AlarmsAdapter(List<AlarmsDisplayDataHolder> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item_list, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {

        AlarmsDisplayDataHolder item = datalist.get(position);

        holder.txtHabitName.setText(item.getHabit().getName());


        holder.area2.setOnClickListener(v -> {

        });


        holder.area1.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return datalist != null ? datalist.size() : 0;
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView txtHabitName;
        LinearLayout area1, area2;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            txtHabitName = itemView.findViewById(R.id.txtHabitName);
            area1 = itemView.findViewById(R.id.area1);
            area2 = itemView.findViewById(R.id.area2);
        }
    }
}