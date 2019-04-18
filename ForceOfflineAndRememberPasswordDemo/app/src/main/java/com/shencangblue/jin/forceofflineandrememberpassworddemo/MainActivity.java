package com.shencangblue.jin.forceofflineandrememberpassworddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {
    private Button sendBtn;
    public static final String FORCE_OFFLINE = "com.shencangblue.jin.forceofflineandrememberpassworddemo.FORCE_OFFLINE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendBtn = (Button)findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FORCE_OFFLINE);
                sendBroadcast(intent);
            }
        });
    }
}
