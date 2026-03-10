package com.example.habittracker;

import android.app.Application;

import com.example.habittracker.di.AppContainer;

public class MyApplication extends Application {

    public AppContainer container;

    @Override
    public void onCreate() {
        super.onCreate();
        // Cria a instância do container **uma vez** para toda a aplicação
        container = new AppContainer(this);
    }

}
