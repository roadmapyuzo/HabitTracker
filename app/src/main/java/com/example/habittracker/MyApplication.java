package com.example.habittracker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.habittracker.di.AppContainer;
import com.example.habittracker.infra.notification.NotificationChannelConfig;

public class MyApplication extends Application {

    public AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();

        container = new AppContainer(this);

        createHabitNotificationChannel();

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

}
