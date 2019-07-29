package com.example.lz.Activity;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lz.Dialog.CommomDialog;

import java.io.IOException;

public class ClockActivity extends AppCompatActivity {
    private int count, position,number;
    private String title,content,time;
    private boolean isNew =false;
    private CommomDialog commomDialog;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        initEdit();
        commomDialog = new CommomDialog(this, R.style.dialog, content, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                mediaPlayer.stop();
                finish();
            }
        });
        commomDialog.setTitle("时间到!!!");
        commomDialog.setCancelable(false);
        commomDialog.show();
        MyMediaPlayer();
    }

    private void MyMediaPlayer() {
      // mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this,R.raw.glorydays);
        mediaPlayer.start();
      /*  mediaPlayer.setOnPreparedListener(new PrepareMedia());
        mediaPlayer.setOnCompletionListener(new CompletioonMedia());*/

        /*try {
           // mediaPlayer.setDataSource(String.valueOf(R.raw.glorydays));
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void showDialog() {
        commomDialog = new CommomDialog(this, R.style.dialog, content, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                finish();
            }
        });
        commomDialog.setTitle("时间到!!!");
        commomDialog.setCancelable(false);
        commomDialog.show();
        showDialog();
    }

    private void initEdit() {
        isNew = getIntent().getBooleanExtra("isNew", false);
        Log.i("test","isNew:"+isNew);
        if (isNew == true) {
           // count = Integer.parseInt(getIntent().getStringExtra("count"));
        } else {
            //count = Integer.parseInt(getIntent().getStringExtra("count"));
            position = Integer.parseInt(getIntent().getStringExtra("position"));
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");
            number = Integer.parseInt(getIntent().getStringExtra("number"));
            time = getIntent().getStringExtra("time");
        }
    }
    class PrepareMedia implements MediaPlayer.OnPreparedListener{

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.i("media","开始播放");
            mp.start();
        }
    }
    class CompletioonMedia implements MediaPlayer.OnCompletionListener{

        @Override
        public void onCompletion(MediaPlayer mp) {
            MyMediaPlayer();
        }
    }
}
