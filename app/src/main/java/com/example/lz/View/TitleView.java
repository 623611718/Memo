package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.lz.Activity.R;

/**
 * Created by lz on 2019/7/25.
 */

public class TitleView extends LinearLayout {
    public TitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);
    }
}
