package com.jose.p2system;

import android.app.Application;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication singleton;

    private List<Location> mylocation;

    public List<Location> getMylocation() {
        return mylocation;
    }

    public void setMylocation(List<Location> mylocation) {
        this.mylocation = mylocation;
    }

    public MyApplication getInstance(){
        return singleton;
    }
    public void onCreate(){
        super.onCreate();
        singleton = this;
        mylocation = new ArrayList<>();
    }

}
