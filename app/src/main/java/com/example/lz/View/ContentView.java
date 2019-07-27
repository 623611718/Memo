package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lz.Activity.R;
import com.example.lz.Utils.AndroiodScreenProperty;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentView extends LinearLayout {
    private TextView title_tv;
    private TextView content_tv;
    private TextView clock_img;
    private ClickListener clickListener;
    private LinearLayout content_layout;
    private LinearLayout layout;   //根Layout
    private AndroiodScreenProperty androiodScreenProperty;
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

        layout = (LinearLayout) findViewById(R.id.layout);
        androiodScreenProperty = new AndroiodScreenProperty(context);
        int height = androiodScreenProperty.getAndroiodScreenPropertyHeight();
        int width = androiodScreenProperty.getAndroiodScreenPropertyWidth();
        ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (height*0.09375));
        layout.setLayoutParams(params);
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

    /**
     * 设置时间
     * @param time
     */
    public void setClock_img(String time){
        clock_img.setText(time);
    }
}
