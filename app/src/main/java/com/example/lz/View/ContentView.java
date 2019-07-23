package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lz.Activity.R;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentView extends LinearLayout{
    private TextView title_tv;
    private TextView content_tv;
    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.content_view,this);
        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle_tv(String title){
        title_tv.setText(title);
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent_tv(String content){
        content_tv.setText(content);
    }
}
