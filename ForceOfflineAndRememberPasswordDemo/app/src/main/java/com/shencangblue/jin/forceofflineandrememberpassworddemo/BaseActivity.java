package com.shencangblue.jin.forceofflineandrememberpassworddemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {

    private ForOfflineReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollertor.addActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollertor.removeActivity(this);
    }

     class ForOfflineReceiver extends BroadcastReceiver{

         @Override
         public void onReceive(Context context, Intent intent) {
             AlertDialog.Builder builder = new AlertDialog.Builder(context);
             builder.setTitle("警告");
             builder.setMessage("不好意思，您被强制下线了！请重新登录。");
             builder.setCancelable(false);
             builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     ActivityCollertor.finishAll();
                     Intent intent1 = new Intent(BaseActivity.this,LoginActivity.class);
                     startActivity(intent1);
                 }
             });
             builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {

                 }
             });
             builder.show();
         }
     }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(MainActivity.FORCE_OFFLINE);
        receiver = new ForOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

        }
    }
}
