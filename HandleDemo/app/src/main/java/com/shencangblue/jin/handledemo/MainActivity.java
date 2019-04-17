package com.shencangblue.jin.handledemo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView random_tv = null;
    private Handler mhandler = null;

    @SuppressLint({"SetTextI18n", "HandlerLeak"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        random_tv = (TextView) findViewById(R.id.random_tv);
        random_tv.setText("产生新的随机数" + Math.random());

        mhandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x11) {
                    random_tv.setText("产生新的随机数" + Math.random());
                }
            }

        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Message message = new Message();
                        message.what = 0x11;
                        mhandler.sendMessage(message);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }).start();
    }
}
