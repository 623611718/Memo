package com.example.lz.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.lz.Adapter.ContentAdapter;
import com.example.lz.Bean.ContactsEntity;
import com.example.lz.DB.BtDBHelper;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Service.AlarmClockService;
import com.example.lz.Utils.AndroiodScreenProperty;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.Utils.TimePickerDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ContactsEntity> queryAll_list = new ArrayList<>();
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private BtDbContactManager btDbContactManager;
    private BtDBHelper btDBHelper;
    private ContactsEntity contactsEntity;
    private static int DB_number = 0;
    private AndroiodScreenProperty androiodScreenProperty;
    private int screenWidth = 360, screenHeight = 640;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        changeStatusBarTextColor(true);
        Intent intent = new Intent(this, AlarmClockService.class);
        startService(intent);
        initTime();
       // showTimerPicker();
        initScreenDp();     //获取屏幕dp
        initDB();             //初始化数据库
        initView();             //初始化View
        initActionBar();          //初始化标题栏
        initListView();              //初始化ListView
    }

    private void initTime() {
      Log.i("test","当前时间:"+BasisTimesUtils.getDeviceTime());
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

    private void initScreenDp() {
        androiodScreenProperty = new AndroiodScreenProperty(this);
        screenHeight = androiodScreenProperty.getAndroiodScreenPropertyHeight();
        screenWidth = androiodScreenProperty.getAndroiodScreenPropertyWidth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//这里是调用menu文件夹中的main.xml，在登陆界面label右上角的三角里显示其他功能
        return true;
    }

    private void initDB() {
        btDBHelper = new BtDBHelper(this, "book1.db", null, 1);
        btDBHelper.getWritableDatabase();
        btDbContactManager = BtDbContactManager.getInstance();
        btDbContactManager.init(this);
        contactsEntity = new ContactsEntity();
      /*  contactsEntity.setTitle("111222");
        contactsEntity.setContent("1112221111111111111111111111111111111111111111111");
        contactsEntity.setNumber(0);
        btDbContactManager.save(contactsEntity);
        contactsEntity.setTitle("111222");
        contactsEntity.setContent("1112221111111111111111111111111111111111111111111");
        contactsEntity.setNumber(0);
        btDbContactManager.save(contactsEntity);
        contactsEntity.setTitle("111222");
        contactsEntity.setContent("1112221111111111111111111111111111111111111111111");
        contactsEntity.setNumber(1);
        btDbContactManager.save(contactsEntity);*/
        queryAll_list = btDbContactManager.queryAll(contactsEntity);
        /*if (queryAll_list != null) {
            Log.i("test", "长度:" + queryAll_list.size());
            Log.i("test", "title:" + queryAll_list.get(0).getTitle());
        }*/
        // contactsEntity = new ContactsEntity();
    }

    private ContentAdapter contentAdapter;
    private void initListView() {
        Log.i("test", "queryAll_list:" + queryAll_list);

        if (queryAll_list != null) {
            contentAdapter = new ContentAdapter(this, R.layout.content_listview_item, queryAll_list);
            listView.setAdapter(contentAdapter);
            DB_number = contentAdapter.getCount();
            Log.i("test", "数据条目:" + DB_number);

        }
    }

    /**
     * 时间选择
     */
    private void showTimerPicker() {
        BasisTimesUtils.showTimerPickerDialog(this, true, "请选择时间", 21, 33, true, new BasisTimesUtils.OnTimerPickerListener() {
            @Override
            public void onConfirm(int hourOfDay, int minute) {
                Log.i("test", hourOfDay + ":" + minute);
            }

            @Override
            public void onCancel() {
                Log.i("test", "cancle");
            }
        });
    }


    /**
     * 显示年月日选择器
     */
    private void showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(this, BasisTimesUtils.THEME_HOLO_DARK, "请选择年月日", 2015, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                Log.i("test", year + "-" + month + "-" + dayOfMonth);
            }

            @Override
            public void onCancel() {
                Log.i("test", "cancle");
            }
        });

    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        //考虑 ActionBar和DrawerLayout的联动
        // mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        //   mToggle.syncState();  //同步状态
        //  mDrawerLayout.addDrawerListener(mToggle);//添加监听
    }

    private void initView() {
        // FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((int) (screenWidth*0.9056), FrameLayout.LayoutParams.MATCH_PARENT);
        // params.gravity = Gravity.CENTER_HORIZONTAL;
        //  params.topMargin = 150;

        listView = (ListView) findViewById(R.id.listvew_main);
        //  listView.setLayoutParams(params);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(this, AlarmActivity.class);
                intent.putExtra("count",String.valueOf(contentAdapter.getCount()));
                intent.putExtra("isNew",true);
                intent.putExtra("position",String.valueOf(contentAdapter.getCount()));
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        queryAll_list = btDbContactManager.queryAll(contactsEntity);
        if (queryAll_list != null) {
            contentAdapter.refresh(queryAll_list);
        }
    }

}
