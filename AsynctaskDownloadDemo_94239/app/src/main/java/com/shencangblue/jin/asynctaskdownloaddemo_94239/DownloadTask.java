package com.shencangblue.jin.asynctaskdownloaddemo_94239;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadTask extends AsyncTask {
    private TextView resultTv = null;
    private ProgressBar progressBar = null;

    @Override
    protected Object doInBackground(Object[] objects) {


        try {
            for (int i = 0; i <= 100; i++) {
                publishProgress(i);
                Thread.sleep((int)objects[0]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "下载完毕！";
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
        progressBar.setProgress((int) values[0]);
        progressBar.setVisibility(View.VISIBLE);
        resultTv.setText("当前完成任务百分比："+(int)values[0]+"%");
    }

    public DownloadTask() {

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        resultTv.setText((String)o);
        resultTv.setTextColor(Color.GREEN);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public DownloadTask(TextView textView, ProgressBar progressBar) {
        resultTv = textView;
        this.progressBar = progressBar;
    }
}
