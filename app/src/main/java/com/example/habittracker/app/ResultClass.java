package com.example.habittracker.app;

public class ResultClass<T> {

    private final T data;
    private final String error;

    private ResultClass(T data, String error) {
        this.data = data;
        this.error = error;
    }

    public static <T> ResultClass<T> success(T data) {
        return new ResultClass<>(data, null);
    }

    public static <T> ResultClass<T> failure(String error) {
        return new ResultClass<>(null, error);
    }

    public boolean isSuccess() {
        return error == null;
    }

    public boolean isFailure() {
        return error != null;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}