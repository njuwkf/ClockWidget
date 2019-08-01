package com.example.clockwidget.ClockWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import com.example.clockwidget.R;
import com.example.clockwidget.Utils.SaveUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClockService extends Service {
    private Timer timer;
    private String str_twelvehour = "12小时制";
    private static final String TAG = "ColockService";

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                updateView();
            }
        },0,1000);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateView(){
        //时间格式
        String str_time;
        String str_date = getDateString() + "  " + getDateInWeek();
        long currentTime = System.currentTimeMillis();
        if(str_twelvehour.equals(SaveUtils.getTimeFormat(this))){
            SimpleDateFormat twelve_formatter = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date(currentTime);
            str_time = twelve_formatter.format(date);
            Calendar mCalendar = Calendar.getInstance();
            if(mCalendar.get(Calendar.AM_PM) == 0){
                //上午
                str_date = str_date + " 上午";
            }else {
                //下午
                str_date = str_date + " 下午";
            }
        }else{
            SimpleDateFormat twentyfour_formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(currentTime);
            str_time = twentyfour_formatter.format(date);
        }
        RemoteViews rViews = new RemoteViews(getPackageName(), R.layout.clockwidge_activity);
        //显示当前时间
        Log.d(TAG,"ClockService_time:"+str_time);
        Log.d(TAG,"ClockService_date:"+str_date);
        rViews.setTextViewText(R.id.clock_time,str_time);
        rViews.setTextViewText(R.id.date_time,str_date);
        //时钟颜色变化（从sharepreferences中读取数据）
        if("".equals(SaveUtils.getFontColor(this))){
            Log.d(TAG,"ClockService_fontcolor:"+SaveUtils.getFontColor(this));
            rViews.setTextColor(R.id.clock_time,Color.BLACK);
        }else {
            Log.d(TAG,"ClockService_fontcolor"+SaveUtils.getFontColor(this));
            switch (SaveUtils.getFontColor(this)) {
                case "黑":
                    rViews.setTextColor(R.id.clock_time, Color.BLACK);
                    break;
                case "白":
                    rViews.setTextColor(R.id.clock_time, Color.WHITE);
                    break;
                case "红":
                    rViews.setTextColor(R.id.clock_time, Color.RED);
                    break;
                case "黄":
                    rViews.setTextColor(R.id.clock_time, Color.YELLOW);
                    break;
                case "绿":
                    rViews.setTextColor(R.id.clock_time, Color.GREEN);
                    break;
                case "蓝":
                    rViews.setTextColor(R.id.clock_time, Color.BLUE);
                    break;
                default:
                    break;
            }
        }
        //时钟大小变化（从sharepreferences中读取数据）
        if("".equals(SaveUtils.getFontSize(this))){
            Log.d(TAG,"ClockService_fontsize"+SaveUtils.getFontSize(this));
            rViews.setTextViewTextSize(R.id.clock_time, TypedValue.COMPLEX_UNIT_SP, 40);
        }else {
            Log.d(TAG,"ClockService_fontsize"+SaveUtils.getFontSize(this));
            String str_fontsize = SaveUtils.getFontSize(this).substring(0, 2);
            int font_size = Integer.parseInt(str_fontsize);
            rViews.setTextViewTextSize(R.id.clock_time, TypedValue.COMPLEX_UNIT_SP, font_size);
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
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd");
        String[] mstr_now=date_formatter.format(date).toString().split("-");
        String mstr_month = mstr_now[1].replaceAll("^(0+)", "");
        String mstr_day = mstr_now[2].replaceAll("^(0+)", "");
        String mstr_date=mstr_month+"月"+mstr_day+"日";
        return mstr_date;
    }
}
