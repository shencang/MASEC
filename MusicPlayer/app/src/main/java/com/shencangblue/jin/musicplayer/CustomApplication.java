package com.shencangblue.jin.musicplayer;

import android.app.Application;


public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationChannels.createAllNotificationChannels(this);
    }
}
