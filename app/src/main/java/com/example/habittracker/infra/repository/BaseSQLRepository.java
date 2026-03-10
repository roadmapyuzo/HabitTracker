package com.example.habittracker.infra.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.habittracker.infra.db.DatabaseHelper;
import com.example.habittracker.infra.repository.cursor.CursorMapper;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSQLRepository<T> {

    protected final DatabaseHelper dbHelper;
    protected final String tableName;
    protected final String[] columns;
    protected final CursorMapper<T> mapper;

    public BaseSQLRepository(DatabaseHelper dbHelper, String tableName, String[] columns, CursorMapper<T> mapper) {
        this.dbHelper = dbHelper;
        this.tableName = tableName;
        this.columns = columns;
        this.mapper = mapper;
    }

    protected SQLiteDatabase readable() {
        return dbHelper.getReadableDatabase();
    }

    protected SQLiteDatabase writable() {
        return dbHelper.getWritableDatabase();
    }

    protected long insert(ContentValues values) {
        SQLiteDatabase db = writable();
        long id = db.insert(tableName, null, values);
        db.close();
        return id;
    }

    protected int update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = writable();
        int rows = db.update(tableName, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    protected int delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase db = writable();
        int rows = db.delete(tableName, whereClause, whereArgs);
        db.close();
        return rows;
    }

    protected T findById(String idColumn, int id) {
        SQLiteDatabase db = readable();
        Cursor cursor = db.query(tableName, columns, idColumn + " = ?", new String[]{String.valueOf(id)},
                null, null, null);
        T entity = null;
        if (cursor.moveToFirst()) {
            entity = mapper.map(cursor);
        }
        cursor.close();
        db.close();
        return entity;
    }

    protected List<T> findAll(String orderBy) {
        SQLiteDatabase db = readable();
        Cursor cursor = db.query(tableName, columns, null, null, null, null, orderBy);
        List<T> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(mapper.map(cursor));
        }
        cursor.close();
        db.close();
        return list;
    }
}