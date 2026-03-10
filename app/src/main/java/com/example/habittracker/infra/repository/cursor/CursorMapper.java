package com.example.habittracker.infra.repository.cursor;

import android.database.Cursor;

public interface CursorMapper<T> {

    T map(Cursor cursor);

}
