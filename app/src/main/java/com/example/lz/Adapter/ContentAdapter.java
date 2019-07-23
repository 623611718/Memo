package com.example.lz.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lz.Activity.R;
import com.example.lz.Bran.ContentBean;
import com.example.lz.View.ContentView;

import java.util.List;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentAdapter extends ArrayAdapter {
    private Context context;
    private List<ContentBean> list;
    private final  int resourceId;
    public ContentAdapter(@NonNull Context context, int resource, List<ContentBean> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
        this.resourceId = resource;
    }

    /**
     * 刷新数据源
     * @param list
     */
    public void  refresh(List<ContentBean> list){
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
    public View getView(int position, View convertView,ViewGroup parent){
        ContentBean contentBean = (ContentBean) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.content_listview_item,null);
        ContentView title = (ContentView) view.findViewById(R.id.content);
        title.setTitle_tv(contentBean.getTitle());
        title.setContent_tv(contentBean.getContent());
        return view;
    }
}
