package com.example.lz.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.lz.Activity.AlarmActivity;
import com.example.lz.Activity.ClockActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmClockService extends Service {
    public AlarmClockService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.i("service","onCreate");
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i("service","onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private Date date,date_now;
    private int count, position,number;
    private String title,content,time;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("service","onStartCommand");
        count = Integer.parseInt(intent.getStringExtra("count"));
        position = Integer.parseInt(intent.getStringExtra("position"));
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        number = Integer.parseInt(intent.getStringExtra("number"));
        time = intent.getStringExtra("time");

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("LongRunningService", "executed at " + new Date().toString());
            }
        }).start();
        Calendar calendar = Calendar.getInstance();

        Log.i("test", "时间:" + DateFormat.format("yyyy-MM-dd kk:mm:",
                calendar.getTime()).toString());
        try {
            Date calendarDate = calendar.getTime();
           // String time = DateFormat.format("yyyy-MM-dd kk:mm", calendarDate).toString();
            Log.i("test", "转换后的时间:" + time);
// 新建一个SimpleDateFormat对象，时间的格式
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd kk:mm");
            // format.parse()返回一个Date的数据类型
            date = format.parse(time);
            date_now = format.parse(DateFormat.format("yyyy-MM-dd kk:mm", calendarDate).toString());
            //返回从1970-01-01 00:00:00到date表示时间的毫秒数
            Log.i("test", "date.getTime:" + date.getTime());
            // format.parse()返回一个Date的数据类型
            Log.i("test", "format.parse():"
                    + format.parse("2014-08-29 15:56"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
       // int anHour = 1 * 1000;
       // long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        long triggerAtTime = Math.abs(date.getTime()-date_now.getTime());
        Log.i("test","当前时间:"+date_now.getTime());
        Log.i("test","选择的时间:"+date.getTime());
        Log.i("test", "间隔时间:" + triggerAtTime);
        Intent i = new Intent(this, ClockActivity.class);
        i.putExtra("count",count);
        i.putExtra("position",String.valueOf(position));
        i.putExtra("isNew",false);
        i.putExtra("title",String.valueOf(title));
        i.putExtra("content",String.valueOf(content));
        i.putExtra("number",String.valueOf(number));
        i.putExtra("time",time);
        Log.i("service","number:"+number);
        PendingIntent pi = PendingIntent.getActivity(this, number, i, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC, date.getTime(), pi);//1min后返回执行
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i("service","onDestroy");
    }
}
