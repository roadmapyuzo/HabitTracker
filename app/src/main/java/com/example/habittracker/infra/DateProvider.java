package com.example.habittracker.infra;

import java.time.LocalDate;

public class DateProvider {

    public LocalDate today() {

        return LocalDate.now();

    }

}
