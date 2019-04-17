package com.shencangblue.jin.intentdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MessageActivity extends AppCompatActivity {

    EditText phoneNum = null;
    EditText contentET = null;
    Button myBtn = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        phoneNum = (EditText)findViewById(R.id.phoneNum);
        contentET = (EditText)findViewById(R.id.contentET);
        myBtn = (Button)findViewById(R.id.myBtn);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = phoneNum.getText().toString();
                String content = contentET.getText().toString();
                Intent intent = new Intent();
                intent.setData(Uri.parse("smsto:"+mobile));
                intent.putExtra("sms_body",content);
                startActivity(intent);
            }
        });

    }}
