package com.example.lz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lz.Activity.AlarmActivity;
import com.example.lz.Activity.R;
import com.example.lz.Bran.ContactsEntity;
import com.example.lz.Bran.ContentBean;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.Utils.TimePickerDialog;
import com.example.lz.View.ContentView;

import java.util.List;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentAdapter extends ArrayAdapter {
    private Context context;
    private List<ContactsEntity> list;
    private final  int resourceId;
    private TimePickerDialog timePickerDialog;
    public ContentAdapter(@NonNull Context context, int resource, List<ContactsEntity> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
        this.resourceId = resource;
        timePickerDialog = new TimePickerDialog(context);
    }

    /**
     * 刷新数据源
     * @param list
     */
    public void  refresh(List<ContactsEntity> list){
        this.list=list;
        notifyDataSetChanged();
    }

    /**
     * 获取数据数量
     * @return
     */
    @Override
    public int getCount(){
        return list.size();
    }

    /**
     * 获取指定position的数据
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position){
        return list.get(position);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        ContactsEntity contentBean = (ContactsEntity) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.content_listview_item,null);
        ContentView title = (ContentView) view.findViewById(R.id.content);
        title.setTitle_tv(contentBean.getTitle());
        title.setContent_tv(contentBean.getContent());
        title.setCustomOnClickListener(new ContentView.ClickListener() {
            @Override
            public void Click(View v) {
                switch (v.getId()){
                    case R.id.clock_img:
                        timePickerDialog.showDateAndTimePickerDialog();
                        break;
                    case R.id.content_layout:
                        Intent intent = new Intent(context, AlarmActivity.class);

                        intent.putExtra("count",String.valueOf(getCount()));
                        intent.putExtra("position",String.valueOf(position));
                        context.startActivity(intent);
                        break;
                }
            }
        });
        return view;
    }
    /**
     * 显示年月日选择器
     */
    private void showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(context, BasisTimesUtils.THEME_HOLO_DARK, "请选择年月日", 2015, 1, 1, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                Log.i("test",year + "-" + month + "-" + dayOfMonth);
            }

            @Override
            public void onCancel() {
                Log.i("test","cancle");
            }
        });

    }
}
