package com.shencangblue.jin.musicplayer.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.shencangblue.jin.musicplayer.Activity.MainActivity;

import java.io.IOException;

public class MusicService extends Service {
    //0x11 表示停止， 0x12表示播放 0x13表示暂停
    int status = 0x11;
    private MediaPlayer mediaPlayer;
    private AssetManager assetManager;
    private String []musics = new String[]{"an.mp3","hb.mp3","wows.mp3"};
    //当前正在播放的歌曲的对应的索引值
    int current=0;
    private  ServiceReceiver serviceReceiver;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private class ServiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int control = intent.getIntExtra("control",-1);
            switch (control){
                case 1:{
                    if (status == 0x11) {//停止播放状态
                        prepareAndPlay(musics[current]);
                        status = 0x12;

                    }else if (status == 0x12){//播放状态
                        mediaPlayer.pause();
//                        mediaPlayer.start();
                        status = 0x13;
                    }else if (status ==0x13){//暂停状态
                        mediaPlayer.start();
                        status=0x12;
                    }break;
                }
                case 2:{
                    mediaPlayer.stop();
                    status = 0x11;
                    break;
                }
            }

            Intent sendIntent = new Intent(MainActivity.UPDATE);
            sendIntent.putExtra("update",status);
            sendIntent.putExtra("current",current);
            sendBroadcast(sendIntent);
        }
    }

    private void prepareAndPlay(String music) {

        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(music);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        assetManager =getAssets();
        serviceReceiver = new ServiceReceiver();
        //动态注册serviceReceiver
        IntentFilter intentFilter = new IntentFilter(MainActivity.CONTROL);
        registerReceiver(serviceReceiver,intentFilter);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                current++;

                if (current>=3){
                    current =0;
                }

                Intent intent = new Intent(MainActivity.UPDATE);
                intent.putExtra("current",current);
                sendBroadcast(intent);
                prepareAndPlay(musics[current]);

            }
        });


    }
}
