package com.example.habittracker.infra;

import com.example.habittracker.app.DateProvider;

import java.time.LocalDate;

public class DateProviderImpl implements DateProvider {

    public LocalDate today() {

        return LocalDate.now();

    }

}
