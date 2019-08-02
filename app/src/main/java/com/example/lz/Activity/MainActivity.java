package com.example.lz.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lz.Adapter.ContentAdapter;
import com.example.lz.Bean.ContactsEntity;
import com.example.lz.DB.BtDBHelper;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Utils.AndroiodScreenProperty;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.View.BottomView;
import com.example.lz.View.TitleView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private List<ContactsEntity> queryAll_list = new ArrayList<>();  //用于保存查询到的数据
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private BtDbContactManager btDbContactManager;
    private BtDBHelper btDBHelper;
    private ContactsEntity contactsEntity;     //数据库javabean类
    private static int DB_number = 0;
    private AndroiodScreenProperty androiodScreenProperty;      //屏幕dp操作类
    private int screenWidth = 360, screenHeight = 640;
    private TitleView titleView;  //自定义标题栏
    private BottomView bottomView;    //编辑状态下的底部栏
    private int edit_titleView = 1;        //标题栏状态,判断是否进入编辑状态  1为正常  0位编辑状态
    private EditText search_et;
    private LinearLayout focusble_layout;  //设置抢占焦点的 layout

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();//隐藏掉整个ActionBar，包括下面的Tabs
        changeStatusBarTextColor(true);
        initScreenDp();     //获取屏幕dp
        initDB();             //初始化数据库
        initView();             //初始化View
        initActionBar();          //初始化标题栏
        initListView();              //初始化ListView
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

    /**
     *
     */
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

    /**
     * 初始化数据库
     */
    private void initDB() {
        btDBHelper = new BtDBHelper(this, "book1.db", null, 1);
        btDBHelper.getWritableDatabase();
        btDbContactManager = BtDbContactManager.getInstance();
        btDbContactManager.init(this);
        contactsEntity = new ContactsEntity();
        queryAll_list = btDbContactManager.queryAll(contactsEntity);
    }

    private ContentAdapter contentAdapter;

    /**
     * listview 初始化
     */
    private void initListView() {
        if (queryAll_list != null) {
            contentAdapter = new ContentAdapter(this, R.layout.content_listview_item, queryAll_list);
            listView.setAdapter(contentAdapter);
            DB_number = contentAdapter.getCount();
            listView.setOnItemLongClickListener(new OnItemLongClickListener1());

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            titleView.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
        } else if (!hasFocus) {
            titleView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }
    }


    /**
     * listview 的长按点击事件
     */
    class OnItemLongClickListener1 implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i("test", "长按");
            contentAdapter.showCheck();            //显示选择框
            bottomView.setVisibility(View.VISIBLE);        //显示底部栏
            floatingActionButton.setVisibility(View.GONE);        //隐藏悬浮按钮
            titleView.setTitleView(edit_titleView);               //更改标题   箭头+退出 替换为 X
            edit_titleView = 0;                                     //设置标题状态  0表示编辑 1表示正常
            return true;
        }
    }


    private ActionBarDrawerToggle mToggle;
    private DrawerLayout mDrawerLayout;

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);        // 给左上角图标的左边加上一个返回的图标 。对应ActionBar.DISPLAY_HOME_AS_UP
        //考虑 ActionBar和DrawerLayout的联动
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mToggle.syncState();  //同步状态
        mDrawerLayout.addDrawerListener(mToggle);//添加监听
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            Log.i("test", "1111" + mToggle);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class TitleOnClistener implements TitleView.ClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.back_bt:
                    if (edit_titleView == 0) {
                        titleView.setTitleView(edit_titleView);  //传入 0 显示 标题栏
                        edit_titleView = 1;                         //设置状态为1 进入正常状态
                        bottomView.setVisibility(View.GONE);           //底部栏设置不可见
                        floatingActionButton.setVisibility(View.VISIBLE);    //悬浮按钮设置可见
                        contentAdapter.hideCheck();                          //隐藏选择框
                    } else if (edit_titleView == 1) {
                        finish();
                    }
                    break;
            }

        }
    }


    /**
     * 长按listview 后显示的底部栏的点击事件
     */
    class BottomOnClickListener implements BottomView.ClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete_bt:          //删除按钮点击事件
                    contentAdapter.deleteItem();        //删除数据库
                    queryAll_list = btDbContactManager.queryAll(contactsEntity);      //重新查询数据库
                    contentAdapter.refresh(queryAll_list);                               //刷新
                    break;
                case R.id.all_bt:              //全选按钮点击事件
                    contentAdapter.selectAllCheck();             //判断是否全选
                    break;
            }
        }
    }

    /**
     * 初始化View控件
     */
    private void initView() {
        listView = (ListView) findViewById(R.id.listvew_main);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
        titleView = (TitleView) findViewById(R.id.titleview);
        titleView.setCustomOnClickListener(new TitleOnClistener());
        bottomView = (BottomView) findViewById(R.id.bottomview);
        bottomView.setCostomClickListener(new BottomOnClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        search_et = (EditText) findViewById(R.id.search_et);
        search_et.setOnFocusChangeListener(this);
        search_et.addTextChangedListener(new TextChangedListener());
        focusble_layout = (LinearLayout) findViewById(R.id.focusble_layout);
    }

    private List<ContactsEntity> search_list = new ArrayList<>();
    private List<ContactsEntity> search_list2 = new ArrayList<>();
    private String search = null;

    private class TextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            search = search_et.getText().toString();
            search_list.clear();
            search_list2.clear();
            for (ContactsEntity contactsEntity : queryAll_list) {
                if (contactsEntity.getContent().contains(search)) {
                    search_list.add(contactsEntity);
                    if (search.length() == 6) {
                        contactsEntity.setContent(search + "...");
                        search_list2.add(contactsEntity);
                    } else {
                        contactsEntity.setContent(contactsEntity.getContent().substring(contactsEntity.getContent().lastIndexOf(search),
                                contactsEntity.getContent().length()));
                        search_list2.add(contactsEntity);
                    }
                }
                Log.i("test", "内容:" + contactsEntity.getContent());
                Log.i("test", "搜索的内容:" + search);
                Log.i("test", "是否包含:" + contactsEntity.getContent().contains(search));
            }
            contentAdapter.refresh(search_list2);
            //contentAdapter.setContentText(search_list2);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (search_et.getText().toString().length() == 0) {
                    focusble_layout.setFocusable(true);
                    focusble_layout.requestFocus();
                    focusble_layout.requestFocusFromTouch();
                    focusble_layout.setFocusableInTouchMode(true);
                }
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:               //悬浮按钮
                Intent intent = new Intent(this, AlarmActivity.class);
                intent.putExtra("count", String.valueOf(contentAdapter.getCount()));
                intent.putExtra("isNew", true);
                intent.putExtra("position", String.valueOf(contentAdapter.getCount()));
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
