package com.shencangblue.jin.forceofflineandrememberpassworddemo;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollertor {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);

    }
    public static void finishAll(){
        for (Activity activity:activities){
            if (!activity.isFinishing()){
                activity.finish();
            }

        }activities.clear();
    }

    public static List<Activity> getActivities() {
        return activities;
    }

    public static void setActivities(List<Activity> activities) {
        ActivityCollertor.activities = activities;
    }
}
