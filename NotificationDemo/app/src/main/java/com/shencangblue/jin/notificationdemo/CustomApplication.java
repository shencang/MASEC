package com.shencangblue.jin.notificationdemo;

import android.app.Application;


public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannels.createAllNotificationChannels(this);
    }
}
