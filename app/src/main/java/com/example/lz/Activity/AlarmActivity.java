package com.example.lz.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lz.DB.BtDBHelper;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Bean.ContactsEntity;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.View.TitleView;

import java.util.List;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private BtDBHelper btDBHelper;
    private ContactsEntity contactsEntity;
    private EditText editText;
    private BtDbContactManager btDbContactManager;
    private int count, position,number;
    private String title,content,time;
    private TitleView titleView;
    private boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getSupportActionBar().hide();//隐藏标题栏
        changeStatusBarTextColor(true);
        initView();
        initDB();
        initEdit();
    }
    private void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            } else {
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
            }
        }
    }
    private void initEdit() {
        isNew = getIntent().getBooleanExtra("isNew", false);
        if (isNew == true) {
            count = Integer.parseInt(getIntent().getStringExtra("count"));
        } else {
            count = Integer.parseInt(getIntent().getStringExtra("count"));
            position = Integer.parseInt(getIntent().getStringExtra("position"));
            title = getIntent().getStringExtra("title");
            content = getIntent().getStringExtra("content");
            number = Integer.parseInt(getIntent().getStringExtra("number"));
            time = getIntent().getStringExtra("time");
          //  List<ContactsEntity> list = btDbContactManager.queryAll(position);
            editText.setText(content);

        }
    }

    private void initDB() {

        btDBHelper = new BtDBHelper(this, "book1.db", null, 1);
        btDBHelper.getWritableDatabase();
        btDbContactManager = BtDbContactManager.getInstance();
        btDbContactManager.init(this);
        contactsEntity = new ContactsEntity();
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.editText);
        editText.setSelection(editText.getText().toString().length());
        titleView = (TitleView) findViewById(R.id.title);
        titleView.setCustomOnClickListener(new TitleView.ClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                String title;

                //输入内容大于0,进行保存
                if (content.length() > 0) {
                    if (content.length() >= 6) {
                        title = content.substring(0, 6);
                    } else {
                        title = content;
                    }
                    contactsEntity.setTitle(title);
                    contactsEntity.setContent(content);
                    if (isNew == true) {                               //如果是新建,按照count进行保存,如果不是则按照position进行更新
                        contactsEntity.setNumber(count);
                        contactsEntity.setTime( BasisTimesUtils.getDeviceTime());
                    } else {
                        contactsEntity.setNumber(number);
                        contactsEntity.setTime(time);
                    }
                    btDbContactManager.save(contactsEntity);
                }

                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //  myDatabaseHelper.getWritableDatabase();
            //  btDbContactManager.save(contactsEntity);
            //    List<ContactsEntity> list =btDbContactManager.queryAll("dx");
            //     Log.i("test","长度:"+list.size());
             /*   String content = editText.getText().toString();
                String title;
                if (content.length() >= 6) {
                    title = content.substring(0, 6);
                } else {
                    title = content;
                }
                contactsEntity.setTitle(title);
                contactsEntity.setContent(content);
                contactsEntity.setNumber(position);
                btDbContactManager.save(contactsEntity);*/
        }
    }
}
