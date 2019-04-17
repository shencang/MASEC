package com.shencangblue.jin.intentdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HttpActivity extends AppCompatActivity {
    EditText httpET;
    Button  httpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);

        httpET = (EditText)findViewById(R.id.httpNum);
        httpBtn = (Button)findViewById(R.id.httpBtn);
        httpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = httpET.getText().toString();
                Intent intent = new Intent();
                intent.setData(Uri.parse("http://"+mobile));
          //      intent.putExtra("sms_body",content);
                startActivity(intent);
            }
        });
    }
}
