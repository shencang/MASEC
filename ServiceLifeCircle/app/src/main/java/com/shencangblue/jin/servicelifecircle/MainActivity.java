package com.shencangblue.jin.servicelifecircle;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button startBtn,stopBtn,bindBtn,unBindBtn,getDataBtn;

    private ServiceConnection serviceConncetion;
    private static final String TAG ="MainActivity";
    private MyService.MyBinder myBinder = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent = new Intent(MainActivity.this,MyService.class);

        serviceConncetion = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG,"MainActivity's onServiceConnected invoked!");
                myBinder = (MyService.MyBinder)service;
                myBinder.getCount();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG,"MainActivity's onServiceDisconnected invoked!");


            }

        };

        startBtn=(Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               intent.setAction("cn.edu.nuc.MY_SERVICE");
                startService(intent);
            }
        });
        stopBtn=(Button)findViewById(R.id.stopBtn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);

            }
        });

        bindBtn= (Button)findViewById(R.id.bindBtn);
        bindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(intent,serviceConncetion,Service.BIND_AUTO_CREATE);
            }
        });
        unBindBtn=(Button)findViewById(R.id.unBindBtn);
        unBindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(serviceConncetion);

            }
        });
        getDataBtn=(Button)findViewById(R.id.get_data_Btn);
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,
                        "count="+myBinder.getCount(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
