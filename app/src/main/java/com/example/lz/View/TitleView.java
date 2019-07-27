package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lz.Activity.R;
import com.example.lz.Utils.AndroiodScreenProperty;

/**
 * Created by lz on 2019/7/25.
 */

public class TitleView extends LinearLayout {
    private TextView title_tv;
    private Button more_bt,back_bt;
    private int height,width;
    private AndroiodScreenProperty androiodScreenProperty;
    private ClickListener clickListener;
    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);

        initView();
        androiodScreenProperty = new AndroiodScreenProperty(context);
        height = androiodScreenProperty.getAndroiodScreenPropertyHeight();
        width = androiodScreenProperty.getAndroiodScreenPropertyWidth();
       // initMore_bt();
    }

    private void initView() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        more_bt = (Button) findViewById(R.id.mote_bt);
        back_bt = (Button) findViewById(R.id.back_bt);
        more_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener !=null){
                    clickListener.onClick(v);
                }
            }
        });
        back_bt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onClick(v);
                }
            }
        });
    }

    private void initMore_bt() {
        LayoutParams params = new LayoutParams((int)(width*0.067),(int)(height*0.3636));
        params.leftMargin= (int) (width*0.8889);
       // params.gravity = G
    }

    /**
     * 设置一个接口
     */
    public interface ClickListener{
        void onClick(View v);
    }
    /**
     * 这个方法等同于setOnClickListener
     *
     * @param clickListener 这个接口就是OnClickListener
     */
    public void setCustomOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

}
