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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmClockService extends Service {
    public AlarmClockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("LongRunningService", "executed at " + new Date().toString());
            }
        }).start();
        Calendar calendar = Calendar.getInstance();
        Log.i("test", (Calendar.YEAR) + "年"
                + calendar.get(Calendar.MONTH) + "月"
                + calendar.get(Calendar.DAY_OF_MONTH) + "日"
                + calendar.get(Calendar.HOUR_OF_DAY) + "时"
                + calendar.get(Calendar.MINUTE) + "分"
                + calendar.get(Calendar.SECOND) + "秒" + "\n今天是星期"
                + calendar.get(Calendar.DAY_OF_WEEK) + "是今年的第"
                + calendar.get(Calendar.WEEK_OF_YEAR) + "周");
        Log.i("test", "时间:" + DateFormat.format("yyyy-MM-dd kk:mm:ss",
                calendar.getTime()).toString());
        try {
            Date calendarDate = calendar.getTime();
            String time = DateFormat.format("yyyy-MM-dd kk:mm:ss", calendarDate).toString();
            Log.i("test", "转换后的时间:" + time);
// 新建一个SimpleDateFormat对象，时间的格式
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd kk:mm:ss");
            // format.parse()返回一个Date的数据类型
            Date date = format.parse(time);
            //返回从1970-01-01 00:00:00到date表示时间的毫秒数
            Log.i("test", "date.getTime:" + date.getTime());
            // format.parse()返回一个Date的数据类型
            Log.i("test", "format.parse():"
                    + format.parse("2014-08-29 15:56:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 1 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Log.i("test", "SystemClock.elapsedRealtime():" + SystemClock.elapsedRealtime());
       // Intent i = new Intent(this, AlarmActivity.class);
      //  PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
     //   manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//1min后返回执行
        return super.onStartCommand(intent, flags, startId);
    }

}
