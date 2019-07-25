package com.example.lz.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.lz.Adapter.ContentAdapter;
import com.example.lz.Bean.ContactsEntity;
import com.example.lz.DB.BtDBHelper;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Service.AlarmClockService;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.Utils.TimePickerDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener, View.OnClickListener {
    private List<ContactsEntity> queryAll_list = new ArrayList<>();
    private ListView listView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private FloatingActionButton floatingActionButton;
    private BtDbContactManager btDbContactManager;
    private BtDBHelper btDBHelper;
    private ContactsEntity contactsEntity;
    private static  int DB_number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        //这里是在登录界面label上右上角添加三个点，里面可添加其他功能

        Intent intent = new Intent(this, AlarmClockService.class);
        startService(intent);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this);
        showTimerPicker();
        initDB();
        initView();
        initActionBar();
        initListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
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
        contactsEntity.setTitle("111222");
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
        btDbContactManager.save(contactsEntity);
        queryAll_list = btDbContactManager.queryAll(contactsEntity);
        if (queryAll_list != null) {
            Log.i("test", "长度:" + queryAll_list.size());
            Log.i("test", "title:" + queryAll_list.get(0).getTitle());
        }
        // contactsEntity = new ContactsEntity();
    }

    private void initListView() {

        if (queryAll_list != null) {
            ContentAdapter contentAdapter = new ContentAdapter(this, R.layout.content_listview_item, queryAll_list);
            listView.setAdapter(contentAdapter);
            DB_number = contentAdapter.getCount();
            Log.i("test","数据条目:"+DB_number);

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
        listView = (ListView) findViewById(R.id.listvew_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Intent intent = new Intent(this, AlarmActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            Log.i("test", "1111" + mToggle);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

}
