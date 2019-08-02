package com.example.lz.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.lz.Activity.AlarmActivity;
import com.example.lz.Activity.R;
import com.example.lz.Bean.ContactsEntity;
import com.example.lz.DB.BtDbContactManager;
import com.example.lz.Service.AlarmClockService;
import com.example.lz.Utils.BasisTimesUtils;
import com.example.lz.Utils.TimePickerDialog;
import com.example.lz.View.ContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/23.
 */

public class ContentAdapter extends ArrayAdapter {
    private Context mContext;
    private List<ContactsEntity> list;
    private final int resourceId;
    private TimePickerDialog timePickerDialog;
    private int position1;

    public ContentAdapter(@NonNull Context context, int resource, List<ContactsEntity> list) {
        super(context, resource, list);
        this.mContext = context;
        this.list = list;
        this.resourceId = resource;
        timePickerDialog = new TimePickerDialog(context);
    }

    /**
     * 刷新数据源
     *
     * @param list
     */
    public void refresh(List<ContactsEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void setContentText(List<ContactsEntity> list){
        List<ContactsEntity> search_list = new ArrayList<>();
        search_list=this.list;
        this.list = list;
        notifyDataSetChanged();
        this.list=search_list;
    }

    /**
     * 获取数据数量
     *
     * @return
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 获取指定position的数据
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    private List<ViewHolder> list_view = new ArrayList<>();

    public void showCheck() {
        if (viewHolder != null)
            for (int i = 0; i < list_view.size(); i++) {
                list_view.get(i).checkBox.setVisibility(View.VISIBLE);
            }
    }

    public void hideCheck() {
        if (viewHolder != null)
            for (int i = 0; i < list_view.size(); i++) {
                list_view.get(i).checkBox.setVisibility(View.GONE);
            }
    }

    public void selectAllCheck(){
        int number = 0;
        if (viewHolder != null)
            for (int i = 0; i < list_view.size(); i++) {
                if (list_view.get(i).checkBox.isChecked()){
                   number+=1;
                }
            }
            if (number == list_view.size()){
                for (int i = 0; i < list_view.size(); i++) {
                    list_view.get(i).checkBox.setChecked(false);
                }
            }else {
                for (int i = 0; i < list_view.size(); i++) {
                    list_view.get(i).checkBox.setChecked(true);
                }
            }
    }
    public void deleteItem(){
        BtDbContactManager btDbContactManager = BtDbContactManager.getInstance();
        btDbContactManager.init(mContext);
        if (viewHolder != null)
            for (int i = 0; i < list_view.size(); i++) {
                if (list_view.get(i).checkBox.isChecked()){
                    btDbContactManager.delete(list.get(i).getNumber());
                }
            }
    }
    private String title, content;
    private int number;
    private ViewHolder viewHolder;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = null;
        viewHolder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.contentView = (ContentView) view.findViewById(R.id.content);
            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
            list_view.add(viewHolder);

        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.contentView.setTitle_tv(list.get(position).getTitle());
        viewHolder.contentView.setContent_tv(list.get(position).getContent());
        viewHolder.contentView.setClock_img(list.get(position).getTime());
        viewHolder.contentView.setCustomOnClickListener(new ContentView.ClickListener() {
            @Override
            public void Click(View v) {
                switch (v.getId()) {
                    case R.id.clock_img:
                        //timePickerDialog.showDateAndTimePickerDialog();
                        position1 = position;
                        showYearMonthDayPicker();
                        break;
                    case R.id.content_layout:
                        Intent intent = new Intent(mContext, AlarmActivity.class);
                        title = list.get(position).getTitle();
                        content = list.get(position).getContent();
                        number = list.get(position).getNumber();
                        intent.putExtra("count", String.valueOf(getCount()));
                        intent.putExtra("position", String.valueOf(position));
                        intent.putExtra("isNew", false);
                        intent.putExtra("title", String.valueOf(title));
                        intent.putExtra("content", String.valueOf(content));
                        intent.putExtra("number", String.valueOf(number));
                        intent.putExtra("time", list.get(position).getTime());
                        mContext.startActivity(intent);
                        break;
                }
            }
        });
        return view;
    }

    static class ViewHolder {
        RadioGroup radioGroup;
        ContentView contentView;
        CheckBox checkBox;
    }

    private String time;

    /**
     * 显示年月日选择器
     */
    private void showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(mContext, BasisTimesUtils.THEME_HOLO_DARK, "请选择年月日", 2019, 7, 29, new BasisTimesUtils.OnDatePickerListener() {

            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                Log.i("test", year + "-" + month + "-" + dayOfMonth);
                time = year + "-" + month + "-" + dayOfMonth + " ";
                showTimerPicker();
            }

            @Override
            public void onCancel() {
                Log.i("test", "cancle");
                time = null;
            }
        });

    }

    /**
     * 时间选择
     */
    private void showTimerPicker() {
        BasisTimesUtils.showTimerPickerDialog(mContext, true, "请选择时间", 21, 33, true, new BasisTimesUtils.OnTimerPickerListener() {
            @Override
            public void onConfirm(int hourOfDay, int minute) {
                Log.i("test", hourOfDay + ":" + minute);
                time = time + hourOfDay + ":" + minute;
                list.get(position1).setTime(time);
                refresh(list);
                BtDbContactManager btDbContactManager = BtDbContactManager.getInstance();
                btDbContactManager.init(mContext);
                btDbContactManager.save(list.get(position1));
                Log.i("test", "选择的时间:" + time);

                Intent intent = new Intent(mContext, AlarmClockService.class);
                title = list.get(position1).getTitle();
                content = list.get(position1).getContent();
                number = list.get(position1).getNumber();
                intent.putExtra("count", String.valueOf(getCount()));
                intent.putExtra("position", String.valueOf(position1));
                intent.putExtra("isNew", false);
                intent.putExtra("title", String.valueOf(title));
                intent.putExtra("content", String.valueOf(content));
                intent.putExtra("number", String.valueOf(number));
                intent.putExtra("time", list.get(position1).getTime());
                mContext.startService(intent);
            }

            @Override
            public void onCancel() {
                Log.i("test", "cancle");
                time = null;
            }
        });
    }
}
