package com.example.lz.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.lz.Activity.R;

/**
 * Created by Administrator on 2019/7/31.
 */

public class BottomView extends LinearLayout {
    private Button delete_bt,all_bt;
    private ClickListener clickListener;
    public BottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_view,this);
        initView();
    }

    private void initView() {
        delete_bt = (Button) findViewById(R.id.delete_bt);
        all_bt = (Button) findViewById(R.id.all_bt);
        all_bt.setOnClickListener(new OnClickListener1());
        delete_bt.setOnClickListener(new OnClickListener1());
    }
    class OnClickListener1 implements OnClickListener{

        @Override
        public void onClick(View v) {
            if (clickListener !=null){
                clickListener.onClick(v);
            }
        }
    }
   public interface ClickListener{
        void onClick(View v);
    }
    public void setCostomClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
}
