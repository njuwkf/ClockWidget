package com.example.clockwidget.ClockWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import com.example.clockwidget.DrawView.ViewClock;
import com.example.clockwidget.R;
import com.example.clockwidget.Utils.SaveUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @auther 吴科烽
 * @date 2019-07-30
 * @describle TODO
 **/

public class ClockService extends Service {
    private Timer timer;
    private static final String str_twelvehour = "12小时制";
    private static final String TAG = "ClockService";
    private static final String TAG_LifeCycle = "ClockService_LifeCycle";
    private static final String str_am = "上午";
    private static final String str_pm = "下午";
    private static final String str_color_black = "黑";
    private static final String str_color_white = "白";
    private static final String str_color_red = "红";
    private static final String str_color_yellow = "黄";
    private static final String str_color_green = "绿";
    private static final String str_color_blue = "蓝";
    private Bitmap mbitmap = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG_LifeCycle,"ClockService_onCreate");
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
        RemoteViews rViews = new RemoteViews(getPackageName(), R.layout.clockwidge_activity);
        String str_time="";
        String str_date = getDateString() + "  " + getDateInWeek();
        //系统时间
        long currentTime = System.currentTimeMillis();

        //时间格式（24小时制or12小时制）
        if(str_twelvehour.equals(SaveUtils.getTimeFormat(this))){
            SimpleDateFormat twelve_formatter = new SimpleDateFormat("hh:mm:ss");
            Date date = new Date(currentTime);
            str_time = twelve_formatter.format(date);
            Calendar mCalendar = Calendar.getInstance();
            if(mCalendar.get(Calendar.AM_PM) == 0){
                //上午
                str_date = str_date + " "+str_am;
            }else {
                //下午
                str_date = str_date + " "+str_pm;
            }
        }else{
            SimpleDateFormat twentyfour_formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(currentTime);
            str_time = twentyfour_formatter.format(date);
        }
        //显示当前时间
        Log.d(TAG,"ClockService_time:"+str_time);
        Log.d(TAG,"ClockService_date:"+str_date);
        rViews.setTextViewText(R.id.clock_time,str_time);
        rViews.setTextViewText(R.id.date_time,str_date);


        //时钟字体颜色变化（从sharepreferences中读取数据）
        Log.d(TAG,"ClockService_fontcolor:"+SaveUtils.getFontColor(this));
        switch (SaveUtils.getFontColor(this)) {
            case str_color_black:
                rViews.setTextColor(R.id.clock_time, Color.BLACK);
                break;
            case str_color_white:
                rViews.setTextColor(R.id.clock_time, Color.WHITE);
                break;
            case str_color_red:
                rViews.setTextColor(R.id.clock_time, Color.RED);
                break;
            case str_color_yellow:
                rViews.setTextColor(R.id.clock_time, Color.YELLOW);
                break;
            case str_color_green:
                rViews.setTextColor(R.id.clock_time, Color.GREEN);
                break;
            case str_color_blue:
                rViews.setTextColor(R.id.clock_time, Color.BLUE);
                break;
            default:
                break;
        }


        //时钟字体大小变化（从sharepreferences中读取数据）
        Log.d(TAG,"ClockService_fontsize"+SaveUtils.getFontSize(this));
        String str_fontsize = SaveUtils.getFontSize(this).substring(0, 2);
        int font_size = Integer.parseInt(str_fontsize);
        rViews.setTextViewTextSize(R.id.clock_time, TypedValue.COMPLEX_UNIT_SP, font_size);


        //图形时钟
        /*SimpleDateFormat twelve_formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(currentTime);
        String view_time = twelve_formatter.format(date);
        ViewClock.drawBitmap(mbitmap,view_time);
        rViews.setImageViewBitmap(R.id.view_clock,mbitmap);*/

        //刷新
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cName = new ComponentName(getApplicationContext(), ClockProvider.class);
        manager.updateAppWidget(cName,rViews);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG_LifeCycle,"ClockService_onDestroy");
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
