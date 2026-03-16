package com.example.habittracker.infra.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;

import com.example.habittracker.R;
import com.example.habittracker.domain.alarms.Alarm;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int habitId = intent.getIntExtra("habitId", 0);
        int hour = intent.getIntExtra("hour", 0);
        int minute = intent.getIntExtra("minute", 0);

        boolean canNotify = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            canNotify = ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;
        }

        if (canNotify) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "habit_channel")
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentTitle("Hora do hábito!")
                    .setContentText("Hora de realizar o hábito #" + habitId + " às " + hour + ":" + minute)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(habitId, builder.build());
        }

        Alarm alarm = new Alarm(habitId, habitId, hour, minute);
        new NotificationSchedulerImpl(context).schedule(alarm);
    }
}