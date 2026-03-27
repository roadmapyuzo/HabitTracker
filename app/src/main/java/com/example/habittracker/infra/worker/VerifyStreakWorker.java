package com.example.habittracker.infra.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.habittracker.app.orchestration.VerifyStreaksUseCase;
import com.example.habittracker.di.AppContainer;

public class VerifyStreakWorker extends Worker {

    private final VerifyStreaksUseCase verifyStreaksUseCase;

    public VerifyStreakWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);

        AppContainer container = new AppContainer(context);
        this.verifyStreaksUseCase = container.getVerifyStreaksUseCase();
    }

    @NonNull
    @Override
    public Result doWork() {
        verifyStreaksUseCase.execute();
        return Result.success();
    }
}