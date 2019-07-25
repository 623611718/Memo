package com.example.lz.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lz.DB.BtDBHelper;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Bran.ContactsEntity;

import java.util.List;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {
    private Button confirm_bt;
    private BtDBHelper btDBHelper;
    private ContactsEntity contactsEntity;
    private EditText editText;
    private BtDbContactManager btDbContactManager;
    private int count, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initView();
        initDB();
        initEdit();
    }

    private void initEdit() {
      //  String s= getIntent().getStringExtra("a");
     //   Log.i("test","sss:"+s);
        count = Integer.parseInt(getIntent().getStringExtra("count"));
        position = Integer.parseInt(getIntent().getStringExtra("position"));
        List<ContactsEntity> list = btDbContactManager.queryAll(position);
        editText.setText(list.get(0).getContent());

    }

    private void initDB() {

        btDBHelper = new BtDBHelper(this, "book1.db", null, 1);
        btDBHelper.getWritableDatabase();
        btDbContactManager = BtDbContactManager.getInstance();
        btDbContactManager.init(this);
        contactsEntity = new ContactsEntity();
    }

    private void initView() {
        confirm_bt = (Button) findViewById(R.id.button);
        confirm_bt.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                //  myDatabaseHelper.getWritableDatabase();
              //  btDbContactManager.save(contactsEntity);
                //    List<ContactsEntity> list =btDbContactManager.queryAll("dx");
                //     Log.i("test","长度:"+list.size());
                String content = editText.getText().toString();
                String title;
                if (content.length() >= 6) {
                    title = content.substring(0, 6);
                } else {
                    title = content;
                }
                contactsEntity.setTitle(title);
                contactsEntity.setContent(content);
                contactsEntity.setNumber(position);
                btDbContactManager.save(contactsEntity);
        }
    }
}
