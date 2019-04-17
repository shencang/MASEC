package com.shencangblue.jin.sendmessagedemo;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

/**
 * 常用的系统的服务有
 * 1.smsManager
 * 2.TelephonyManager 获取网络状态，读取sim卡信息
 * 3.AudioManager音频管理器 音量大小，静音
 * 4.AlarmManager 手机闹钟服务，全局定时器
 * 5.vibrator 震动
 */

public class MainActivity extends AppCompatActivity {

    EditText numberET, smsET;
    Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numberET = (EditText) findViewById(R.id.numberEt);
        smsET = (EditText) findViewById(R.id.smsEt);

        sendBtn = (Button) findViewById(R.id.sendBtn);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS},
                1);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = numberET.getText().toString().trim();
                String content = smsET.getText().toString();
                if (num.length() < 5 && content.length() < 1) {

                    Toast.makeText(MainActivity.this, "输入错误请重新输入", Toast.LENGTH_LONG).show();


                } else {
                    SmsManager smsManager = SmsManager.getDefault();

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this
                            , 0
                            , new Intent()
                            , 0);
                    List<String> msgs = smsManager.divideMessage(content);
                    for (String msg : msgs) {
                        smsManager.sendTextMessage(num, null, msg, pendingIntent, null);
                    }
                    Toast.makeText(MainActivity.this, "短信发送完毕", Toast.LENGTH_LONG).show();

                }


            }
        });
    }
}
