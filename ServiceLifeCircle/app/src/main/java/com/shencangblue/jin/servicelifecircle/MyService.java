package com.shencangblue.jin.servicelifecircle;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.util.Log;

import java.util.Arrays;

import static android.content.Intent.ACTION_ANSWER;
import static android.content.Intent.ACTION_DEFAULT;
import static android.content.Intent.ACTION_DELETE;

@SuppressLint("Registered")
public class MyService extends Service {
    private static final String TAG ="MyService";
    private boolean quit =false;//计数子线程的循环控制变量
    private int count = 0;//计数器变量
    private MyBinder myBinder = new MyBinder();

    NotificationManager nm ;
    @Nullable
    @Override
    //IBinder接口,用于实现Server和其他组件的数据交互
    //该接口有一个实现类Binder，通常我们会写一个Binder类的子类，让该子类实现数据的交互
    public IBinder onBind(Intent intent) {
//        R.string.myServiceMessage
        Log.i(TAG,"myService's onBind invoked!");
        return myBinder;
    }



    @Override
    public void onCreate() {
        Log.i(TAG,"myService's onCreate invoked!");
        super.onCreate();

        new  Thread(new Runnable() {
            @Override
            public void run() {
                while (!quit){

                    try {
                        count++;
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"myService's onDestroy invoked!");
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Intent intent1 = new Intent(this,MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(MyService.this
//                ,0
//                ,intent1
//                ,0);
//
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("this is content title");
//        builder.setContentText("this is content text")
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
//                .setContentIntent(pendingIntent);
//
//        Notification notification = builder.build();
//        startForeground(444,notification);

        sendSimpleNotification(this,nm);

        Log.i(TAG,"myService's onStartCommand invoked!");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG,"myService's onRebind invoked!");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"myService's onUnbind invoked!");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{

        public MyBinder(){
            Log.i(TAG," MyBinder's constructor invoked!");
        }
        public int getCount(){
            return count;
        }

    }

    public void sendSimpleNotification(Context context,NotificationManager nm) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context,MyService.class);
        intent.setAction(ACTION_DEFAULT);
        PendingIntent pi = PendingIntent.getService(context,0,intent,0);
        //创建删除通知时发送的广播
        Intent deleteIntent = new Intent(context,MyService.class);
        deleteIntent.setAction(ACTION_DELETE);
        PendingIntent deletePendingIntent = PendingIntent.getService(context,0,deleteIntent,0);
        //创建通知
        Notification.Builder nb = new Notification.Builder(context,NotificationChannels.LOW)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle("Simple notification")
                //设置通知内容
                .setContentText("Demo for simple notification !")
                //设置点击通知后自动删除通知
                .setAutoCancel(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置通知右侧的大图标
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_round))
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置删除通知时的响应事件
                .setDeleteIntent(deletePendingIntent);
        //发送通知
        nm.notify(NotificationManager.IMPORTANCE_DEFAULT,nb.build());
    }

}
