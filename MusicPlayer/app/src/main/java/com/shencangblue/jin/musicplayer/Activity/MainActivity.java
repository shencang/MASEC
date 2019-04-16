package com.shencangblue.jin.musicplayer.Activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.shencangblue.jin.musicplayer.Notificaitons;
import com.shencangblue.jin.musicplayer.NotificationContentWrapper;
import com.shencangblue.jin.musicplayer.Service.MusicService;
import com.shencangblue.jin.musicplayer.R;

public class MainActivity extends AppCompatActivity {
    private ImageView stopBtn, playBtn;
    private TextView authorTv, songNameTv;
    private MyListener myListener ;
    public  static final  String CONTROL="com.shencang.jin.musicplayer.CONTROL";
    public  static final  String UPDATE="com.shencang.jin.musicplayer.UPDATE";
    //0x11 表示停止， 0x12表示播放 0x13表示暂停
    private int status= 0x11;
    //当前正在播放的歌曲的对应的索引值
    private int current= 0;
    private  String []someName = new String[]{
            "TearVid-Long mix"
            ,"君だったら"
            ,"World of Warships OST 79"};
    private  String[]authors = new String[]{
            "HAPPY BIRTHDAY"
            ,"VicetoneCozi Zuehlsdorff",
            "Various Artists"};
    private ActivityReceiver activityReceiver;

    private Context mContext;
    private NotificationManager mNM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        stopBtn = (ImageView) findViewById(R.id.stopBtn);
        playBtn = (ImageView) findViewById(R.id.playBtn);
        songNameTv = (TextView) findViewById(R.id.songNaneTv);
        authorTv = (TextView) findViewById(R.id.authorTv);

        myListener = new MyListener();

        stopBtn.setOnClickListener(myListener);
        playBtn.setOnClickListener(myListener);

        activityReceiver = new ActivityReceiver();
        IntentFilter intentFilter = new IntentFilter(UPDATE);
        registerReceiver(activityReceiver,intentFilter);

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        startService(intent);




    }

    private class ActivityReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            status= intent.getIntExtra("update",-1);
            current=intent.getIntExtra("current",-1);
            Log.d("MainActivity",Integer.toString(current));
            if (current>=0&&current<3){
                songNameTv.setText(someName[current]);
                authorTv.setText(authors[current]);

            }
            switch (status){
                case 0x11:{
                    playBtn.setImageResource(R.drawable.play);
                    break;
                }
                case 0x12:{
                    playBtn.setImageResource(R.drawable.pause);
                    break;
                }
                case 0x13:{
                    playBtn.setImageResource(R.drawable.play);
                    break;
                }

            }
        }
    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CONTROL);
            switch (v.getId()) {
                case R.id.playBtn: {
                    intent.putExtra("control", 1);
                    //playBtn.setImageIcon(Icon.createWithResource(MainActivity.this.toString(),R.drawable.pause));
                    break;
                }
                case R.id.stopBtn: {
                    intent.putExtra("control", 2);
                    break;
                }

            }
            sendBroadcast(intent);
        }
    }
}
