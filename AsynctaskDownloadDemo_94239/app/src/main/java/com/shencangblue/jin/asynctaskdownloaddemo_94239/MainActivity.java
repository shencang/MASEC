package com.shencangblue.jin.asynctaskdownloaddemo_94239;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button loadBtn =null;
    private TextView resultTv = null;
    private ProgressBar progressBar= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        loadBtn = (Button)findViewById(R.id.loadBtn);
        resultTv= (TextView)findViewById(R.id.result);
        progressBar =(ProgressBar)findViewById(R.id.bar);
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadTask downloadTask = new DownloadTask(resultTv,progressBar);
                downloadTask.execute(1000);
            }
        });
    }
}
