package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lz.Activity.R;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentView extends LinearLayout {
    private TextView title_tv;
    private TextView content_tv;
    private TextView clock_img;
    private ClickListener clickListener;

    private LinearLayout content_layout;
    public ContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.content_view, this);
        title_tv = (TextView) findViewById(R.id.title_tv);
        content_tv = (TextView) findViewById(R.id.content_tv);
        clock_img = (TextView) findViewById(R.id.clock_img);
        clock_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.Click(v);
                }
            }
        });
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        content_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.Click(v);
                }
            }
        });
    }


    /**
     * 设置一个Click接口
     */
    public interface ClickListener {
        void Click(View v);//参数不知道怎么传可以先不传
    }

    /**
     * 这个方法等同于setOnClickListener
     *
     * @param clickListener 这个接口就是OnClickListener
     */
    public void setCustomOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle_tv(String title) {
        title_tv.setText(title);
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContent_tv(String content) {
        content_tv.setText(content);
    }
}
