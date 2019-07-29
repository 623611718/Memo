package com.example.lz.Activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.lz.Dialog.CommomDialog;

public class ClockActivity extends AppCompatActivity {
    private int count, position,number;
    private String title,content,time;
    private boolean isNew =false;
    private CommomDialog commomDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        initEdit();
        commomDialog = new CommomDialog(this, R.style.dialog, content, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                finish();
            }
        });
        commomDialog.setTitle("时间到!!!");
        commomDialog.setCancelable(false);
        commomDialog.show();

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
}
