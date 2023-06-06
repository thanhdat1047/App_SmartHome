package com.example.pbl5demo1.model;

public class DataSingleton {
    private static DataSingleton instance = null;
    private String data;

    private DataSingleton() {
    }

    public static DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
