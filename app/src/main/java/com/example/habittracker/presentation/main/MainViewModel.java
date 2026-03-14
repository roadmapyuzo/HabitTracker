package com.example.habittracker.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<String> message = new MutableLiveData<>();

    public MainViewModel() {
        message.setValue("Bem vindo ao App");
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public void onButtonClicked() {
        message.setValue("Você clicou no botão!");
    }
}
