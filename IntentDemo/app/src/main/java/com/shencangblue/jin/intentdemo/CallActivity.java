package com.shencangblue.jin.intentdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CallActivity extends AppCompatActivity {

    EditText inputNumeberET = null;
    Button callBtn = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        inputNumeberET = (EditText) findViewById(R.id.inputNumberET);
        callBtn = (Button) findViewById(R.id.callBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String mobile = inputNumeberET.getText().toString();
//                Intent intent = new Intent();
//                intent.setData(Uri.parse("tel:" + mobile));
//                startActivity(intent);

                if (ContextCompat.checkSelfPermission(CallActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CallActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    call();
                }

            }
        });
    }

    private void call() {
        try {
            Uri uri = Uri.parse("tel:" + inputNumeberET.getText().toString());
            Intent intents = new Intent(Intent.ACTION_CALL, uri);
            CallActivity.this.startActivity(intents);
        }catch (SecurityException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                } else {
                    Toast.makeText(CallActivity.this, "你", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                break;
        }
    }
}
