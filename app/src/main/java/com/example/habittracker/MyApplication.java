package com.example.habittracker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.habittracker.di.AppContainer;
import com.example.habittracker.infra.notification.NotificationChannelConfig;
import com.example.habittracker.infra.worker.VerifyStreakWorker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    public AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();

        container = new AppContainer(this);

        createHabitNotificationChannel();
        scheduleVerifyStreakWorker();

    }

    private void createHabitNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = NotificationChannelConfig.HABIT_CHANNEL_ID;
            String channelName = NotificationChannelConfig.HABIT_CHANNEL_NAME;
            String channelDescription = NotificationChannelConfig.HABIT_CHANNEL_DESC;
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void scheduleVerifyStreakWorker() {
        long initialDelay = calculateInitialDelay();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                VerifyStreakWorker.class,
                24, TimeUnit.HOURS
        )
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                "verify_streak_worker",
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
        );
    }

    private long calculateInitialDelay() {
        Calendar now = Calendar.getInstance();
        Calendar nextRun = Calendar.getInstance();
        nextRun.set(Calendar.HOUR_OF_DAY, 0);
        nextRun.set(Calendar.MINUTE, 1);
        nextRun.set(Calendar.SECOND, 0);
        nextRun.set(Calendar.MILLISECOND, 0);

        if (now.after(nextRun)) {
            nextRun.add(Calendar.DAY_OF_MONTH, 1);
        }

        return nextRun.getTimeInMillis() - now.getTimeInMillis();
    }

}
