package com.example.xyzreader.dependency;

import android.app.Application;
import android.content.Context;


public class App extends Application {

    protected static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = getAppComponent(this);
    }

    public static AppComponent appComponent() {
        return appComponent;
    }

    private static AppComponent getAppComponent(Context context) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(context))
                .build();
    }
}
