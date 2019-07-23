package com.example.lz.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.lz.Adapter.ContentAdapter;
import com.example.lz.Bran.ContentBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener {
    private List<ContentBean> content_list = new ArrayList<>();
    private ListView listView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initActionBar();
        for (int i=0;i<10;i++){
            ContentBean list = new ContentBean("title","content");
            content_list.add(list);
        }
        ContentAdapter contentAdapter = new ContentAdapter(this,R.layout.content_listview_item,content_list);
        listView.setAdapter(contentAdapter);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        //考虑 ActionBar和DrawerLayout的联动
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout, R.string.open,R.string.close);
        mToggle.syncState();  //同步状态
        mDrawerLayout.addDrawerListener(mToggle);//添加监听
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listvew_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (mToggle.onOptionsItemSelected(item)){
            Log.i("test","1111"+mToggle);
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
