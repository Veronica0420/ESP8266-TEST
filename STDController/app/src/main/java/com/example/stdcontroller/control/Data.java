package com.example.stdcontroller.control;

import android.app.Application;

public class Data extends Application {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String mtype) {
        this.type = mtype;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        setType("Lcd");
    }
}
