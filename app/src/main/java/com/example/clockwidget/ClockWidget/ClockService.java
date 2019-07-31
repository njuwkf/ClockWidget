package com.example.clockwidget.ClockWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.clockwidget.ClockWidget.ClockProvider;
import com.example.clockwidget.MyApp;
import com.example.clockwidget.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClockService extends Service {
    private Timer timer;
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm:ss");
    private String[] week={"周一","周二","周三","周四","周五","周六","周日"};
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateView();
            }
        },0,1000);
    }

    private void updateView(){
        //时间
        String str_time = simpleDateFormat.format(new Date());
        String str_date=getDateString()+"  "+getDateInWeek();
        RemoteViews rViews = new RemoteViews(getPackageName(), R.layout.clockwidge_activity);
        //显示当前时间
        Log.e("time",str_time);
        Log.e("date",str_date);
        rViews.setTextViewText(R.id.clock_time,str_time);
        rViews.setTextViewText(R.id.date_time,str_date);
        switch (MyApp.getfontcolor()) {
            case "黑":rViews.setTextColor(R.id.clock_time, Color.BLACK);break;
            case "白":rViews.setTextColor(R.id.clock_time, Color.WHITE);break;
            case "红":rViews.setTextColor(R.id.clock_time, Color.RED);break;
            case "黄":rViews.setTextColor(R.id.clock_time, Color.YELLOW);break;
            case "绿":rViews.setTextColor(R.id.clock_time, Color.GREEN);break;
            case "蓝":rViews.setTextColor(R.id.clock_time, Color.BLUE);break;
            default:break;
        }
        //刷新
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cName = new ComponentName(getApplicationContext(), ClockProvider.class);
        manager.updateAppWidget(cName,rViews);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
    }

    /**
     * 获得周几
     * @param
     * @return
     */
    private String getDateInWeek() {
        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
        String[] mweekdays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        Calendar mCalendar = Calendar.getInstance();
        Date mDate=new Date();
        mCalendar.setTime(mDate);
        int weekday = mCalendar.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (weekday < 0)
            weekday = 0;
        return mweekdays[weekday];
    }
    /**
     * 获得mm月dd日类型的日期
     * @param
     * @return
     */
    private String getDateString(){
        String[] mstr_now=new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString().split("-");
        String mstr_month = mstr_now[1].replaceAll("^(0+)", "");
        String mstr_day = mstr_now[2].replaceAll("^(0+)", "");
        String mstr_date=mstr_month+"月"+mstr_day+"日";
        return mstr_date;
    }
}
