package com.example.habittracker.presentation.alarms;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habittracker.R;
import com.example.habittracker.domain.alarms.Alarm;
import com.example.habittracker.presentation.cards.CardsDisplayDataHolder;

import java.util.List;

public class AlarmsAdapter extends RecyclerView.Adapter<AlarmsAdapter.AlarmViewHolder> {

    private List<AlarmsDisplayDataHolder> datalist;
    private AlarmsViewModel viewModel;

    public AlarmsAdapter(List<AlarmsDisplayDataHolder> datalist,AlarmsViewModel viewModel) {
        this.datalist = datalist;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_item_list, parent, false);
        return new AlarmViewHolder(view);
    }

    public void updateData(List<AlarmsDisplayDataHolder> newDataList) {
        this.datalist = newDataList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {

        AlarmsDisplayDataHolder item = datalist.get(position);

        holder.txtHabitName.setText(item.getHabit().getName());

        LayoutInflater inflater = LayoutInflater.from(holder.itemView.getContext());

        holder.subitemarea.removeAllViews();

        if (item.getHabit().isActiveAlarms()) {

            holder.btnAlarm.setBackgroundResource(R.drawable.alarm_button_on);

        } else {
            holder.btnAlarm.setBackgroundResource(R.drawable.alarm_button);
        }

        holder.btnAlarm.setOnClickListener(v -> {

            v.postDelayed(() -> {
                if (item.getHabit().isActiveAlarms()) {
                    viewModel.deactivateAlarms(item.getHabit());
                } else {
                    viewModel.activateAlarms(item.getHabit());
                }
            }, 300);

        });

        for (Alarm alarm : item.getAlarms()) {


            View alarmCard = inflater.inflate(R.layout.alarm_subitem_list, holder.subitemarea, false);

            TextView txtAlarmTime = alarmCard.findViewById(R.id.txtTime);

            String formatedTime;

            formatedTime = String.valueOf(alarm.getHour()) + ":"+ String.valueOf(alarm.getMinute());

            txtAlarmTime.setText(formatedTime);

            LinearLayout deleteButton = alarmCard.findViewById(R.id.area2);
            deleteButton.setOnClickListener(v -> {

                viewModel.deleteAlarm(alarm);

            });

            holder.subitemarea.addView(alarmCard);


        }


    }

    @Override
    public int getItemCount() {
        return datalist != null ? datalist.size() : 0;
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView txtHabitName;
        LinearLayout area1;
        LinearLayout subitemarea;

        LinearLayout btnAlarm;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            txtHabitName = itemView.findViewById(R.id.txtHabitName);
            area1 = itemView.findViewById(R.id.area1);
            subitemarea = itemView.findViewById(R.id.subitemArea);
            btnAlarm = itemView.findViewById(R.id.area2);
        }
    }
}