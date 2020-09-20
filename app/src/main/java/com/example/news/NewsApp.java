package com.example.news;

import android.app.Application;

import com.example.news.di.AppComponent;
import com.example.news.di.DaggerAppComponent;

public class NewsApp extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.create();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
